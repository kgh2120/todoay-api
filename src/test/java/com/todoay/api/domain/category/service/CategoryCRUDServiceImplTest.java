package com.todoay.api.domain.category.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.category.dto.CategorySaveRequestDto;
import com.todoay.api.domain.category.dto.CategorySaveResponseDto;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@SpringBootTest
@Transactional
class CategoryCRUDServiceImplTest {
    @Autowired
    AuthRepository authRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired CategoryCRUDService categoryCRUDService;

    static Auth testAuth;

    @PostConstruct
    void setTestAuth() {
        testAuth = authRepository.findByEmail("test@naver.com").get();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(testAuth, "", testAuth.getAuthorities()));
    }

    @Test
    void addCategory() {
        // given
        String name = "test_category_name";
        String color = "123123";
        int orderIndex = 10;

        // when
        CategorySaveResponseDto responseDto = categoryCRUDService.addCategory(
                CategorySaveRequestDto.builder()
                        .name(name)
                        .color(color)
                        .orderIndex(orderIndex)
                        .build()
        );

        Category category = categoryRepository.findById(responseDto.getId()).orElse(null);

        // then
        Assertions.assertNotNull(responseDto.getId());
        Assertions.assertNotNull(category);
        Assertions.assertEquals(name, category.getName());
        Assertions.assertEquals(color, category.getColor());
        Assertions.assertEquals(orderIndex, category.getOrderIndex());
    }
}