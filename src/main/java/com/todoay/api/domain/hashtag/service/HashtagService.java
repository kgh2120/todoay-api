package com.todoay.api.domain.hashtag.service;

import com.todoay.api.domain.hashtag.dto.HashtagSearchResponseDto;

public interface HashtagService {


    HashtagSearchResponseDto searchHashtag(String name, int pageNum, int quantity);


}
