package com.todoay.api.domain.hashtag.service;

import com.todoay.api.domain.hashtag.dto.HashtagAutoCompleteResponseDto;
import com.todoay.api.domain.hashtag.dto.HashtagSearchResponseDto;

public interface HashtagService {


    // 검색해서 5개를 미리 출력한다.
    HashtagAutoCompleteResponseDto searchHashtagAutoComplete(String name);

    // 페이지로 검색하기

    HashtagSearchResponseDto searchHashtag(String name, int pageNum);


}
