package com.todoay.api.domain.profile.service;

import com.todoay.api.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;



//    // 회원정보 저장
//    public Long save(ProfileSaveDto profileSaveDto) {
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        profileSaveDto.setPassword(encoder.encode(profileSaveDto.getPassword()));
//
//        return profileRepository.save(UserInfo.builder()
//                .email(infoDto.getEmail())
//                .auth(infoDto.getAuth())
//                .password(infoDto.getPassword()).build()).getCode();
//    }


}
