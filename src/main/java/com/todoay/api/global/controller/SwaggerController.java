package com.todoay.api.global.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Hidden
@Controller
public class SwaggerController {

    @GetMapping ("/docs")
    public String showSwaggerDocs() {
        return "redirect:/swagger-ui/index.html";
    }
}
