package com.todoay.api.domain.profile.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.todoay.api.domain.profile.exception.FileTypeMismatchException;
import com.todoay.api.domain.profile.exception.FileUploadErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AmazonS3ServiceImpl implements AmazonS3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String uploadImage(MultipartFile multipartFile, String prevImgUrl) {
        if(multipartFile.isEmpty())
            return null;
        if(!multipartFile.getContentType().contains("image"))
            throw new FileTypeMismatchException();

        try {

            if (prevImgUrl != null) {
                remove(prevImgUrl);
            }
            File file = convertMultipartFileToFile(multipartFile)
                    .orElseThrow();

            String imageUrl = upload(file);

            removeFile(file);
            return imageUrl;
        } catch (IOException e) {
            throw new FileUploadErrorException();
        }
    }

    private Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)){
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }

    private String upload(File file) {
        String dirName = createRandomName();
        return putS3(file,dirName);
    }
    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return getS3(bucket, fileName);
    }


    private String getS3(String bucket, String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void remove(String prevImgUrl) {
        int index = prevImgUrl.lastIndexOf("upload/");
        String key = prevImgUrl.substring(index);

        if (!amazonS3.doesObjectExist(bucket, key)) {
            throw new AmazonS3Exception("Object " +key+ " does not exist!");
        }
        amazonS3.deleteObject(bucket, key);
    }
    private String createRandomName() {
        return  "upload/" + UUID.randomUUID().toString();
    }
    private void removeFile(File file) {
        file.delete();
    }
}
