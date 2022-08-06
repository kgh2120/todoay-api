package com.todoay.api.domain.hashtag.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class HashtagAutoCompleteResponseDto {

    private List<HashtagInfoDto> infos;


}
