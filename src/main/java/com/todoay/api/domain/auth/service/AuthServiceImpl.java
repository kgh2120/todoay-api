package com.todoay.api.domain.auth.service;


import com.todoay.api.domain.auth.dto.AuthSaveDto;
import com.todoay.api.domain.auth.dto.AuthUpdatePasswordReqeustDto;
import com.todoay.api.domain.auth.dto.LoginRequestDto;
import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.profile.exception.EmailNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @Slf4j
@RequiredArgsConstructor @Transactional
public class AuthServiceImpl implements AuthService {

        private final AuthRepository authRepository;

        // spring security 필수 메소드
        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
            return authRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException((email)));
        }

        /**
         * 회원정보 저장
         * @return 저장되는 회원의 PK
         **/

        public Long save(AuthSaveDto authSaveDto) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            authSaveDto.setPassword(encoder.encode(authSaveDto.getPassword()));

            return authRepository.save(authSaveDto.toAuthEntity()).getId();
        }

    @Override
    public void updateAuthPassword(String email, AuthUpdatePasswordReqeustDto dto) {
        Auth auth = authRepository.findByEmail(email)
                .orElseThrow(EmailNotFoundException::new);

        String password = dto.getPassword();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);

        auth.updatePassword(encodedPassword);
        log.info("updated password = {}", auth.getPassword());
    }

    @Override
    public void deleteAuth(String email) {

        Auth auth = authRepository.findByEmail(email)
                .orElseThrow(EmailNotFoundException::new);
        auth.deleteAuth();


    }

        @Override
        public void login(LoginRequestDto loginRequestDto) {
            Auth auth = (Auth)loadUserByUsername(loginRequestDto.getEmail());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (!encoder.matches(loginRequestDto.getPassword(), auth.getPassword())) {
                throw new IllegalArgumentException();  // 나중에 custom exception 추가
            }
        }
}
