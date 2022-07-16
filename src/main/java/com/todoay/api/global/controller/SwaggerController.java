package com.todoay.api.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class SwaggerController {

    @GetMapping ("/docs")
    public String showSwaggerDocs() {
        return "redirect:/swagger-ui/index.html";
    }
}
