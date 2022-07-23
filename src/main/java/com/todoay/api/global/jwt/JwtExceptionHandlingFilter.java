package com.todoay.api.global.jwt;

import com.todoay.api.global.exception.AbstractApiException;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.GlobalErrorCode;
import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.todoay.api.global.exception.GlobalErrorCode.*;

public class JwtExceptionHandlingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            sendError(HttpStatus.UNAUTHORIZED,request,response,e);
        }
    }

    public void sendError(HttpStatus status,HttpServletRequest request, HttpServletResponse response, JwtException e) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=utf-8");

        String errorMsg = "";
        if(e instanceof MalformedJwtException)
            errorMsg =  ErrorResponse.toResponseString(JWT_MALFORMED,request.getRequestURI());
        if(e instanceof ExpiredJwtException)
            errorMsg =  ErrorResponse.toResponseString(JWT_EXPIRED,request.getRequestURI());
        if (e instanceof UnsupportedJwtException)
            errorMsg =  ErrorResponse.toResponseString(JWT_UNSUPPORTED,request.getRequestURI());
         if (e instanceof SignatureException )
             errorMsg =  ErrorResponse.toResponseString(JWT_NOT_VERIFIED,request.getRequestURI());



        response.getWriter().write(errorMsg);

    }
}
