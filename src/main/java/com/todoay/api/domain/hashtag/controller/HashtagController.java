package com.todoay.api.domain.hashtag.controller;

import com.todoay.api.domain.hashtag.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HashtagController {

    private final HashtagService hashtagService;
}
