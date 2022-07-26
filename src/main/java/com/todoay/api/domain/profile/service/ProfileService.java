package com.todoay.api.domain.profile.service;

import com.todoay.api.domain.profile.dto.ProfileReadResponseDto;
import com.todoay.api.domain.profile.dto.ProfileUpdateReqeustDto;

public interface ProfileService {


    // 내 정보 조회

    ProfileReadResponseDto readMyProfile(); // jwt를 받고 jwt Service에서 받아야 할지, controller에서 jwtService를 받고 받아야 할지...

    // 내 정보 변경
    void updateMyProfile(ProfileUpdateReqeustDto dto);

    boolean nicknameExists(String nickname);

}
