package com.todoay.api.domain.profile.service;

import com.todoay.api.domain.profile.dto.ProfileReadResponseDto;
import com.todoay.api.domain.profile.dto.ProfileUpdateReqeustDto;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {


    // 내 정보 조회

    ProfileReadResponseDto readMyProfile();

    // 내 정보 변경
    void updateMyProfile(MultipartFile multipartFile, ProfileUpdateReqeustDto dto);

    boolean nicknameExists(String nickname);

}
