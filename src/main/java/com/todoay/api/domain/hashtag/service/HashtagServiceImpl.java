package com.todoay.api.domain.hashtag.service;


import com.todoay.api.domain.hashtag.dto.HashtagInfoDto;
import com.todoay.api.domain.hashtag.dto.HashtagSearchResponseDto;
import com.todoay.api.domain.hashtag.entity.Hashtag;
import com.todoay.api.domain.hashtag.exception.NoMoreDataException;
import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class HashtagServiceImpl implements HashtagService{

    private final HashtagRepository hashtagRepository;



    @Override
    public HashtagSearchResponseDto searchHashtag(String name, int pageNum, int quantity) {
        PageRequest pageRequest = PageRequest.of(pageNum, quantity);

        Slice<Hashtag> page = hashtagRepository.findHashtagByNameContaining(name, pageRequest);
        List<HashtagInfoDto> infoDtos = page.getContent().stream()
                .map(HashtagInfoDto::new)
                .collect(Collectors.toList());
        if(infoDtos.isEmpty())
            throw new NoMoreDataException();


        return new HashtagSearchResponseDto(page.hasNext(), page.getNumber()+1,infoDtos);
    }
}
