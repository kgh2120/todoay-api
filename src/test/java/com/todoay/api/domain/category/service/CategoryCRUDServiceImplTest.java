package com.todoay.api.domain.category.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.category.dto.CategoryModifyRequestDto;
import com.todoay.api.domain.category.dto.CategorySaveRequestDto;
import com.todoay.api.domain.category.dto.CategorySaveResponseDto;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.exception.NotYourCategoryException;
import com.todoay.api.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class CategoryCRUDServiceImplTest {
    @Autowired
    AuthRepository authRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired CategoryCRUDService categoryCRUDService;

    @Autowired
    EntityManager em;

    Auth testAuth1;
    Auth testAuth2;
    Category testCategory;

    @PostConstruct
    void setTest() {
        testAuth1 = authRepository.findByEmail("test@naver.com").get();
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(testAuth1, "", testAuth1.getAuthorities()));
        CategorySaveResponseDto responseDto = categoryCRUDService.addCategory(CategorySaveRequestDto.builder().name("test_category").color("#123123").orderIndex(0).build());
        testCategory = em.find(Category.class, responseDto.getId());

        testAuth2 = authRepository.save(Auth.builder().email("other@gmail.com").password("asdf1234!").build());

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

    @Test
    void modifyCategory() {
        // given
        String name = "test_category_modified";
        String color = "432432";

        // when
        CategoryModifyRequestDto requestDto = CategoryModifyRequestDto.builder()
                .id(testCategory.getId())
                .name(name)
                .color(color)
                .build();
        categoryCRUDService.modifyCategory(requestDto);
        testCategory = em.find(Category.class, testCategory.getId());

        Assertions.assertEquals(name, testCategory.getName());
        Assertions.assertEquals(color, testCategory.getColor());
        login(testAuth2); // 유저2가 유저1의 카테고리를 변경하려고 하면 NotYourCategoryException 예외 발생
        Assertions.assertThrows(NotYourCategoryException.class, () -> categoryCRUDService.modifyCategory(requestDto));
    }

    void login(Auth auth) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(auth, "", auth.getAuthorities()));
    }
}