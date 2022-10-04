package com.todoay.api.domain.auth.service;


import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.dto.AuthUpdatePasswordRequestDto;
import com.todoay.api.domain.auth.dto.LoginRequestDto;
import com.todoay.api.domain.auth.dto.LoginResponseDto;
import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.exception.*;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.profile.exception.EmailNotFoundException;
import com.todoay.api.domain.profile.exception.NicknameDuplicateException;
import com.todoay.api.global.context.LoginAuthContext;
import com.todoay.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final JwtProvider jwtProvider;

    private final LoginAuthContext loginAuthContext;

    private final BCryptPasswordEncoder encoder;

    // spring security 필수 메소드
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return authRepository.findByEmail(email)
                .orElseThrow(EmailNotFoundException::new);
    }

    /**
     * 회원정보 저장
     * @return 저장되는 회원의 PK
     **/

    @Transactional
    public Long save(AuthSaveDto authSaveDto) {
        validateIsNotDuplicateValueFromUserInput(authSaveDto);
        return createNewAccount(authSaveDto);
    }

    @Transactional
    @Override
    public void updateAuthPassword(AuthUpdatePasswordRequestDto dto) {
        Auth auth = validateIsRightAccessToEditAccount(dto.getOriginPassword());
        updatePassword(dto, auth);
    }

    @Transactional
    @Override
    public void deleteAuth(String password) {
        Auth auth = validateIsRightAccessToEditAccount(password);
        auth.deleteAuth();
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        validateIsRightAccessToService(loginRequestDto);
        return createLoginResponse(loginRequestDto);
    }

    @Override
    public boolean emailExists(String email) {
        return authRepository.findByEmail(email).isPresent();
    }

    private Long createNewAccount(AuthSaveDto authSaveDto) {
        authSaveDto.setPassword(createdNewEncodedPassword(authSaveDto.getPassword()));
        return authRepository.save(authSaveDto.toAuthEntity()).getId();
    }

    private void validateIsNotDuplicateValueFromUserInput(AuthSaveDto authSaveDto) {
        validateIsNotDuplicatedEmail(authSaveDto);
        validateIsNotDuplicatedNickname(authSaveDto);
    }

    private void validateIsNotDuplicatedNickname(AuthSaveDto authSaveDto) {
        authRepository.findByNickname(authSaveDto.getNickname())
                        .ifPresent(auth -> {
                            throw new NicknameDuplicateException();
                        });
    }

    private void validateIsNotDuplicatedEmail(AuthSaveDto authSaveDto) {
        authRepository.findByEmail(authSaveDto.getEmail())
                        .ifPresent(auth -> {
                            throw new EmailDuplicateException();
                        });
    }

    private void updatePassword(AuthUpdatePasswordRequestDto dto, Auth auth) {
        String encodedPassword = createdNewEncodedPassword(dto.getModifiedPassword());
        auth.updatePassword(encodedPassword);
    }

    private Auth validateIsRightAccessToEditAccount(String password) {
        Auth auth = getLoggedInAuthFromJWT();
        validateIsRightPasswordOnEdit(password, auth);
        return auth;
    }

    private void validateIsRightPasswordOnEdit(String password, Auth auth) {
        if (!encoder.matches(password, auth.getPassword())) {
            throw new PasswordNotMatchedException();
        }
    }

    private String createdNewEncodedPassword(String password) {
        return encoder.encode(password);
    }


    private void validateIsRightAccessToService(LoginRequestDto loginRequestDto) {
        Auth auth = validateIsRightLoginId(loginRequestDto);
        validateIsRightPasswordOnLogin(loginRequestDto, auth);
        validateIsVerifiedEmail(auth);
        validateIsDeletedAccount(auth);
    }

    private LoginResponseDto createLoginResponse(LoginRequestDto loginRequestDto) {
        String accessToken = jwtProvider.createAccessToken(loginRequestDto.getEmail());
        String refreshToken = jwtProvider.createRefreshToken(loginRequestDto.getEmail());
        return new LoginResponseDto(accessToken, refreshToken);
    }

    private Auth validateIsRightLoginId(LoginRequestDto loginRequestDto) {
        return authRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(LoginUnmatchedException::new);
    }
    private void validateIsDeletedAccount(Auth auth) {
        if (auth.getDeletedAt() != null) {
            throw new LoginDeletedAccountException();
        }
    }
    private void validateIsVerifiedEmail(Auth auth) {
        if(auth.getEmailVerifiedAt()==null)
            throw new EmailNotVerifiedException();
    }
    private void validateIsRightPasswordOnLogin(LoginRequestDto loginRequestDto, Auth auth) {
        if (!encoder.matches(loginRequestDto.getPassword(), auth.getPassword())) {
            throw new LoginUnmatchedException();
        }
    }

    private Auth getLoggedInAuthFromJWT() {
        return authRepository.findByEmail(loginAuthContext.getLoginAuth().getEmail())
                .orElseThrow(EmailNotFoundException::new);
    }
}
