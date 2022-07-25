package com.todoay.api.domain.profile.dto;

import com.todoay.api.domain.profile.entity.Profile;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ProfileReadResponseDto {

    private String email;
    private String nickname;
    private String imageUrl;
    private String introMsg;




    public static ProfileReadResponseDto createResponseDto(Profile entity) {
       return ProfileReadResponseDto.builder()
               .email(entity.getAuth().getEmail())
               .nickname(entity.getNickname())
               .imageUrl(entity.getImgUrl())
               .introMsg(entity.getIntroMsg())
               .build();
    }
}
