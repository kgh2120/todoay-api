package com.todoay.api.domain.profile.service;

import com.todoay.api.domain.profile.dto.ProfileReadResponseDto;
import com.todoay.api.domain.profile.dto.ProfileUpdateReqeustDto;
import com.todoay.api.domain.profile.entity.Profile;
import com.todoay.api.domain.profile.exception.FileTypeMismatchException;
import com.todoay.api.domain.profile.exception.NicknameDuplicateException;
import com.todoay.api.domain.profile.repository.ProfileRepository;
import com.todoay.api.global.context.LoginAuthContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor @Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final LoginAuthContext loginAuthContext;
    private final AmazonS3Service amazonS3Service;

    @Override
    public ProfileReadResponseDto readMyProfile() {
        Profile profile = getLoggedInProfile();
        return ProfileReadResponseDto.createResponseDto(profile);
    }



    @Transactional
    @Override
    public void updateMyProfile(MultipartFile multipartFile, ProfileUpdateReqeustDto dto) {

        Profile profile = prepareUpdateProfile(multipartFile, dto);
        profile.updateProfile(dto);

    }

    @Override
    public boolean nicknameExists(String nickname) {
        return profileRepository.findProfileByNickname(nickname).isPresent();
    }

    private Profile prepareUpdateProfile(MultipartFile multipartFile, ProfileUpdateReqeustDto dto) {
        Profile profile = getLoggedInProfile();
        String nickname = dto.getNickname();
        if(validateIsNicknameChanged(profile, nickname))
            validateIsNotDuplicatedNickname(nickname);

        if(validateIsClientSendImageFile(multipartFile)){
            uploadProfileImageToS3(multipartFile, dto);
        }
        return profile;
    }

    private void uploadProfileImageToS3(MultipartFile multipartFile, ProfileUpdateReqeustDto dto) {
        String uploadImageUrl = amazonS3Service.uploadImage(multipartFile, dto.getImageUrl());
        if(uploadImageUrl!=null)
            dto.setImageUrl(uploadImageUrl);
    }

    private boolean validateIsClientSendImageFile(MultipartFile multipartFile) {
        if(multipartFile.isEmpty())
            return false;
        if(!Objects.requireNonNull(multipartFile.getContentType()).contains("image"))
            throw new FileTypeMismatchException();

        return true;
    }

    private void validateIsNotDuplicatedNickname(String nickname) {
        profileRepository.findProfileByNickname(nickname)
                .ifPresent(p -> {
                    throw new NicknameDuplicateException();
                }
        );
    }

    private boolean validateIsNicknameChanged(Profile profile, String nickname) {
        return !profile.getNickname().equals(nickname);
    }
    private Profile getLoggedInProfile() {
        return loginAuthContext.getLoginAuth().getProfile();
    }
}
