package com.todoay.api.domain.hashtag.service;


import com.todoay.api.domain.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = false)
@Service
public class HashtagServiceImpl implements HashtagService{

    private final HashtagRepository hashtagRepository;
}
