package com.todoay.api.domain.profile.controller;

import com.todoay.api.domain.profile.dto.ProfileReadResponseDto;
import com.todoay.api.domain.profile.dto.ProfileUpdateReqeustDto;
import com.todoay.api.domain.profile.service.ProfileService;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.ValidErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @Operation(
            summary = "내 정보를 조회한다",
            description = "Jwt 토큰을 통해 얻은 email로 정보를 검색하여, 반환한다.",
            responses = {
                    @ApiResponse(responseCode = "200",description = "성공", content = @Content(schema = @Schema(implementation = ProfileReadResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "AccessToken 만료 ",content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/profile/my")
    public ResponseEntity<ProfileReadResponseDto> getMyProfile() {
        ProfileReadResponseDto profile = profileService.readMyProfile();
        return ResponseEntity.ok(profile);
    }

    @Operation(
            summary = "내 정보를 수정한다",
            description = "Jwt 토큰을 통해 얻은 email 얻은 정보를 dto로 받은 정보로 수정한다. 이때 JWT 토큰으로 인한 오류와 입력 값에 대한 유효성 검사로 인해 오류가 발생할 수 있다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "성공"),
                    @ApiResponse(responseCode = "400", description = "1. 입력 값이 유효성 검사를 통과하지 못했습니다.\t\n " +
                            "2. 이미지 파일이 아닌 파일을 업로드 했습니다.\t\n " +
                            "3. 파일 사이즈 초과", content = @Content(schema = @Schema(implementation = ValidErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "AccessToken 만료 ",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403",description = "허락되지 않은 접근",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "IO 리소스 문제 발생",content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PutMapping("/profile/my")
    public ResponseEntity<Void> updateProfile(@RequestPart("image") MultipartFile multipartFile
            , @RequestPart("profile") @Validated ProfileUpdateReqeustDto dto) {
        profileService.updateMyProfile(multipartFile,dto);
        return ResponseEntity.status(204).build();
    }


}
