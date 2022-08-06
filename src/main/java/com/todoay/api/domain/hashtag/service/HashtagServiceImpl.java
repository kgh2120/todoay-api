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
}
