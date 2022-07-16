package com.todoay.api.global.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class SwaggerController {

    @GetMapping ("/docs")
    public String showSwaggerDocs() {
        return "redirect:/swagger-ui/index.html";
    }
}
