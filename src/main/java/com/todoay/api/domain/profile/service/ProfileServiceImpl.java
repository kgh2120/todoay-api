package com.todoay.api.domain.profile.service;

import com.todoay.api.domain.profile.dto.ProfileReadResponseDto;
import com.todoay.api.domain.profile.dto.ProfileUpdateReqeustDto;
import com.todoay.api.domain.profile.entity.Profile;
import com.todoay.api.domain.profile.exception.EmailNotFoundException;
import com.todoay.api.domain.profile.exception.NicknameDuplicateException;
import com.todoay.api.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor @Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;


    @Override
    public ProfileReadResponseDto readMyProfile(String email) {

        Profile profile = getProfileByEmailOrElseThrowEmailNotFoundException(email);

        return ProfileReadResponseDto.createResponseDto(profile);
    }

    @Transactional
    @Override
    public void updateMyProfile(String email, ProfileUpdateReqeustDto dto) {

        String nickname = dto.getNickname();
        profileRepository.findProfileByNickname(nickname)
                .ifPresent(p -> {
                    throw new NicknameDuplicateException();
                }
        );

        Profile profile = getProfileByEmailOrElseThrowEmailNotFoundException(email);
        profile.updateProfile(dto);
    }

    @Override
    public void nicknameDuplicateCheck(String nickname) {
        profileRepository.findProfileByNickname(nickname)
                .ifPresent(p -> {
                            throw new NicknameDuplicateException();
                        }
                );
    }


    private Profile getProfileByEmailOrElseThrowEmailNotFoundException(String email) {
        return profileRepository.findProfileByAuthEmail(email)
                .orElseThrow(EmailNotFoundException::new);
    }
}
