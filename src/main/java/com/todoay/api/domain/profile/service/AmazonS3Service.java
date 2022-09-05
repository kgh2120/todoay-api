package com.todoay.api.domain.profile.service;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonS3Service {

    // 업로드 기능
    String uploadImage(MultipartFile multipartFile,String prevImgUrl);
}
