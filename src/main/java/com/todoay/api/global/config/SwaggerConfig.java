package com.todoay.api.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@EnableWebMvc

@Configuration
public class SwaggerConfig {

    private static final Set<String> DEFAULT_PRODUCES_CONSUMES = new HashSet<>(Arrays.asList("application/json"));


    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(info());
    }

    private Info info() {
        return new Info()
                .title("Todoay-Api")
                .description("Todoay 어플리케이션 API 문서")
                .version("3.0");


    }


    // @enableWebMvc가 켜져있을 때, /swagger-ui/index.html로 redirect가 되지 않는 경우 발생.
    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }


}
