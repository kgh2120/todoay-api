package com.todoay.api.domain.category.controller;


import com.todoay.api.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CategoryController {

    private final CategoryService categoryService;
}
