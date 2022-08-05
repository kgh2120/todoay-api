package com.todoay.api.domain.hashtag.dto;

import com.todoay.api.domain.hashtag.entity.Hashtag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class HashtagInfoDto {

    private Long id;
    private String name;



    public HashtagInfoDto(Hashtag hashtag) {
        id = hashtag.getId();
        name = hashtag.getName();
    }
}
