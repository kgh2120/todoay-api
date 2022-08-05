package com.todoay.api.domain.hashtag.controller;

import com.todoay.api.domain.hashtag.dto.HashtagAutoCompleteResponseDto;
import com.todoay.api.domain.hashtag.dto.HashtagSearchResopnseDto;
import com.todoay.api.domain.hashtag.service.HashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/hashtag")
public class HashtagController {

    private final HashtagService hashtagService;


    @GetMapping("/auto")
    public ResponseEntity<HashtagAutoCompleteResponseDto> searchHashtagAutoComplete(@RequestParam("name") String name) {
        HashtagAutoCompleteResponseDto dto = hashtagService.searchHashtagAutoComplete(name);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<HashtagSearchResopnseDto> searchHashtag(@RequestParam("name") String name, @RequestParam("pageNum") int pageNum) {
        HashtagSearchResopnseDto dto = hashtagService.searchHashtag(name, pageNum);
        return ResponseEntity.ok(dto);
    }

}
