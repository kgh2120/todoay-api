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

    private final BCryptPasswordEncoder encoder;

    // spring security 필수 메소드
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return authRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException((email)));
    }

    /**
     * 회원정보 저장
     *
     * @return 저장되는 회원의 PK
     **/

    @Transactional
    public Long save(AuthSaveDto authSaveDto) {

        authRepository.findByEmail(authSaveDto.getEmail())
                        .ifPresent(auth -> {
                            throw new EmailDuplicateException();
                        });
        authRepository.findByNickname(authSaveDto.getNickname())
                        .ifPresent(auth -> {
                            throw new NicknameDuplicateException();
                        });

        authSaveDto.setPassword(encoder.encode(authSaveDto.getPassword()));
        return authRepository.save(authSaveDto.toAuthEntity()).getId();
    }

    @Transactional
    @Override
    public void updateAuthPassword(AuthUpdatePasswordRequestDto dto) {


        String email = jwtProvider.getLoginId();
        Auth auth =authRepository.findByEmail(email)
                .orElseThrow(EmailNotFoundException::new);

        if (!encoder.matches(dto.getOriginPassword(),auth.getPassword())) {
            throw new PasswordNotMatchedException();
        }
        String modifiedPassword = dto.getModifiedPassword();
        String encodedPassword = encoder.encode(modifiedPassword);

        auth.updatePassword(encodedPassword);
    }

    @Transactional
    @Override
    public void deleteAuth() {
        String email = jwtProvider.getLoginId();
        Auth auth =authRepository.findByEmail(email)
                .orElseThrow(EmailNotFoundException::new);
        auth.deleteAuth();
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        // 이메일 검증
        Auth auth =authRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(LoginUnmatchedException::new);
        // 비밀번호 검증
        if (!encoder.matches(loginRequestDto.getPassword(), auth.getPassword())) {
            throw new LoginUnmatchedException();  // 나중에 custom exception 추가
        }

        // 이메일 인증 검증
        if(auth.getEmailVerifiedAt()==null)
            throw new EmailNotVerifiedException(); // EMAIL_NOT_VERIFIED_EXCEPTION

        // 삭제 상태 검증
        if (auth.getDeletedAt() != null) {
            throw new LoginDeletedAccountException(); // LOGIN_DELETE_ACCOUNT_EXCEPTION
        }



        String accessToken = jwtProvider.createAccessToken(loginRequestDto.getEmail());
        String refreshToken = jwtProvider.createRefreshToken(loginRequestDto.getEmail());
        return new LoginResponseDto(accessToken,refreshToken);
    }

    @Override
    public boolean emailExists(String email) {
       return authRepository.findByEmail(email).isPresent();
    }

}
