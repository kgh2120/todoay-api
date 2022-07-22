package com.todoay.api.global.jwt;

import com.todoay.api.global.exception.AbstractApiException;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtExcecptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException e) {
            sendError(HttpStatus.UNAUTHORIZED,request,response,e);
        }
    }

    public void sendError(HttpStatus status,HttpServletRequest request, HttpServletResponse response, AbstractApiException e) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=utf-8");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(e.getHttpStatus().value())
                .error(e.getHttpStatus().name())
                .code(e.name())
                .message(e.getDetailMessage())
                .path(request.getRequestURI())
                .build();
        response.getWriter().write(errorResponse.toString());
    }
}
