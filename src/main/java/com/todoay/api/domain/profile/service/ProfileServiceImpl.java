package com.todoay.api.domain.profile.service;

import com.todoay.api.domain.profile.dto.ProfileReadResponseDto;
import com.todoay.api.domain.profile.dto.ProfileUpdateReqeustDto;
import com.todoay.api.domain.profile.entity.Profile;
import com.todoay.api.domain.profile.exception.EmailNotFoundException;
import com.todoay.api.domain.profile.exception.NicknameDuplicateException;
import com.todoay.api.domain.profile.repository.ProfileRepository;
import com.todoay.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor @Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final JwtProvider jwtProvider;


    @Override
    public ProfileReadResponseDto readMyProfile() {

        String email = jwtProvider.getLoginId();
        Profile profile = getProfileByEmailOrElseThrowEmailNotFoundException(email);

        return ProfileReadResponseDto.createResponseDto(profile);
    }

    @Transactional
    @Override
    public void updateMyProfile(ProfileUpdateReqeustDto dto) {
        String email = jwtProvider.getLoginId();
        Profile profile = getProfileByEmailOrElseThrowEmailNotFoundException(email);

        String nickname = dto.getNickname();
        if(!profile.getNickname().equals(nickname))
            profileRepository.findProfileByNickname(nickname)
                    .ifPresent(p -> {
                        throw new NicknameDuplicateException();
                    }
            );
        profile.updateProfile(dto);
    }

    @Override
    public boolean nicknameExists(String nickname) {
        return profileRepository.findProfileByNickname(nickname).isPresent();
    }


    private Profile getProfileByEmailOrElseThrowEmailNotFoundException(String email) {
        return profileRepository.findProfileByAuthEmail(email)
                .orElseThrow(EmailNotFoundException::new);
    }
}
