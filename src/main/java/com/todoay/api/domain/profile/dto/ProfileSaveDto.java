package com.todoay.api.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSaveDto {

    private String nickname;
    private String imgUrl;
    private String introMsg;

    private String auth;
}
