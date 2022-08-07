package com.todoay.api.global.context;

import com.todoay.api.domain.auth.entity.Auth;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginAuthContext {
    public static Auth getLoginAuth() {
        return (Auth) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
