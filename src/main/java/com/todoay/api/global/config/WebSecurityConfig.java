package com.todoay.api.global.config;

import com.todoay.api.domain.auth.service.AuthService;
import com.todoay.api.global.jwt.JwtAuthenticationFilter;
import com.todoay.api.global.jwt.JwtExceptionHandlingFilter;
import com.todoay.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthService authService;
    private final JwtProvider jwtTokenProvider;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public void configure(WebSecurity web){
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/swagger-ui/**", "/v3/api-docs/**", "/email-verification.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .headers().frameOptions().disable()


                .and()
                    .authorizeRequests()
                        .antMatchers("/auth/**", "/signup", "/docs", "/h2-console/**").permitAll()  // 누구나 접근 가능 // profile/my는 permitAll하면 안됨.
                        .antMatchers("/").hasRole("USER")  // USER, ADMIN만 접근 가능
                        .antMatchers("/admin").hasRole("ADMIN")  // ADMIN만
                        .anyRequest().authenticated()  // 나머지 요청들은 권한의 종류에 상관없이 권한이 있어야 접근
                .and()
                    .logout()
                        .logoutSuccessUrl("/")  // 로그아웃 성공시 리다이렉트 주소
                        .invalidateHttpSession(true)

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider,authService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionHandlingFilter(), JwtAuthenticationFilter.class);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
