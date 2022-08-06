package com.todoay.api.domain.hashtag.service;


import com.todoay.api.domain.hashtag.dto.HashtagAutoCompleteResponseDto;
import com.todoay.api.domain.hashtag.dto.HashtagInfoDto;
import com.todoay.api.domain.hashtag.dto.HashtagSearchResponseDto;
import com.todoay.api.domain.hashtag.entity.Hashtag;
import com.todoay.api.domain.hashtag.exception.NoMoreDataException;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import com.todoay.api.domain.todo.entity.DaliyTodo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = false)
@Service
public class HashtagServiceImpl implements HashtagService{

    private final HashtagRepository hashtagRepository;

    @Override
    public HashtagAutoCompleteResponseDto searchHashtagAutoComplete(String name) {
        List<Hashtag> hashtags = hashtagRepository.findTop5ByNameStartsWith(name);
        List<HashtagInfoDto> infoDtos = hashtags.stream()
                .map(HashtagInfoDto::new)
                .collect(Collectors.toList());

        return new HashtagAutoCompleteResponseDto(infoDtos);
    }

    @Override
    public HashtagSearchResponseDto searchHashtag(String name, int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum, 5);

        Slice<Hashtag> page = hashtagRepository.findHashtagByNameStartsWith(name, pageRequest);
        List<HashtagInfoDto> infoDtos = page.getContent().stream()
                .map(HashtagInfoDto::new)
                .collect(Collectors.toList());
        log.info("list = {}",infoDtos);
        if(infoDtos.isEmpty())
            throw new NoMoreDataException();


        return new HashtagSearchResponseDto(page.hasNext(), page.getNumber()+1,infoDtos);
    }

    public void testCode() {
        DaliyTodo daliyTodo = new DaliyTodo(); // 새로 생성 or find한 Todo 객체
        List<HashtagInfoDto> names = new ArrayList<>(); // DTO로 부터 받은 hashtag name 리스트
        List<Hashtag> tags = new ArrayList<>(); // dailyTodo로 전달할 list객체, 연관관계 메서드를 개별 Hashtag로 변경하면
                                                // 아래 코드를 더 줄일 수 있음.
        names.forEach(n -> {
            String name = n.getName();
            hashtagRepository.findByName(name)
                    .ifPresentOrElse(tags::add, // 검색 결과가 존재한다면 연관 관계를 맺을 리스트에 추가
                            () ->{
                        Hashtag save = hashtagRepository.save(new Hashtag(n.getName())); // 없다면 DB에 저장하고 저장.
                        tags.add(save);
                    });
        });
        daliyTodo.associateWithHashtag(tags); // 연관관계를 맺음.
    }
}
