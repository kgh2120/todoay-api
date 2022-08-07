package com.todoay.api.global.context;

import com.todoay.api.domain.auth.entity.Auth;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoginAuthContext {
    public Auth getLoginAuth() {
        return (Auth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
