package com.todoay.api.global.context;

import com.todoay.api.domain.auth.entity.Auth;

public class LoginAuthContext {
    private static Auth loginAuth = null;

    public static void setLoginAuth(Auth loginAuth) {
        LoginAuthContext.loginAuth = loginAuth;
    }

    public static Auth getLoginAuth() {
        return loginAuth;
    }
}
