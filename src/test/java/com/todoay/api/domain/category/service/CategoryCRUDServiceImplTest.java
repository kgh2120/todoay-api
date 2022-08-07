package com.todoay.api.domain.category.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.category.dto.*;
import com.todoay.api.domain.category.dto.CategoryListByTokenResponseDto.CategoryDto;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.exception.CategoryNotFoundException;
import com.todoay.api.domain.category.exception.NotYourCategoryException;
import com.todoay.api.domain.category.repository.CategoryRepository;
import com.todoay.api.global.context.LoginAuthContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

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

    @BeforeEach
    void beforeEach() {
        testAuth1 = authRepository.findByEmail("test@naver.com").get();
        testAuth2 = authRepository.save(Auth.builder().email("other@gmail.com").password("asdf1234!").build());
        login(testAuth1);
    }

    void login(Auth auth) {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(auth, "", auth.getAuthorities()));
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
        CategorySaveResponseDto responseDto = categoryCRUDService.addCategory(CategorySaveRequestDto.builder().name("test_category").color("#123123").orderIndex(0).build());
        Category testCategory = em.find(Category.class, responseDto.getId());

        // when
        CategoryModifyRequestDto requestDto = CategoryModifyRequestDto.builder()
                .name(name)
                .color(color)
                .build();
        categoryCRUDService.modifyCategory(testCategory.getId(), requestDto);
        testCategory = em.find(Category.class, testCategory.getId());

        Assertions.assertEquals(name, testCategory.getName());
        Assertions.assertEquals(color, testCategory.getColor());
        login(testAuth2); // 유저2가 유저1의 카테고리를 변경하려고 하면 NotYourCategoryException 예외 발생
        Category finalTestCategory = testCategory;
        Assertions.assertThrows(NotYourCategoryException.class, () -> categoryCRUDService.modifyCategory(finalTestCategory.getId(), requestDto));
    }

    @Test
    void findCategoryByToken() {
        // given
        String name = "category";
        String color = "123123";

        em.persist(Category.builder().name(name+0).color(color).orderIndex(0).auth(testAuth1).build());
        em.persist(Category.builder().name(name+1).color(color).orderIndex(1).auth(testAuth1).build());

        // when
        CategoryListByTokenResponseDto responseDto0 = categoryCRUDService.findCategoryByToken();

        List<CategoryDto> categories0 = responseDto0.getCategories();
        int size = categories0.size();
        CategoryDto category0 = categories0.get(0);
        CategoryDto category1 = categories0.get(1);
        String name1 = category1.getName();

        // then
        Assertions.assertNotNull(responseDto0);
        Assertions.assertEquals(2, size);
        Assertions.assertNotNull(category0);
        Assertions.assertNotNull(category1);
        Assertions.assertEquals(name + 1, name1);

        // when
        login(testAuth2);
        CategoryListByTokenResponseDto responseDto1 = categoryCRUDService.findCategoryByToken();
        List<CategoryDto> categories1 = responseDto1.getCategories();
        int size1 = categories1.size();

        // then
        Assertions.assertNotNull(responseDto1);
        Assertions.assertEquals(0, size1);
    }

    @Test
    void modifyOrderIndexes() {
        // given
        String categoryName1 = "category1";
        String color1 = "123123";
        Integer orderIndex1 = 1;
        Integer nextOrderIndex1 = 0;
        Long categoryId1 = categoryCRUDService.addCategory(CategorySaveRequestDto.builder().name(categoryName1).color(color1).orderIndex(orderIndex1).build()).getId();

        String categoryName11 = "category11";
        String color11 = "234234";
        Integer orderIndex11 = 2;
        Integer nextOrderIndex11 = 1;
        Long categoryId11 = categoryCRUDService.addCategory(CategorySaveRequestDto.builder().name(categoryName11).color(color11).orderIndex(orderIndex11).build()).getId();

        ////////// 정상적인 흐름
        // when
        ArrayList<CategoryOrderIndexModifyDto.CategoryOrderIndexesDto> orderIndexes = new ArrayList<>();
        orderIndexes.add(new CategoryOrderIndexModifyDto.CategoryOrderIndexesDto(categoryId1, nextOrderIndex1));
        orderIndexes.add(new CategoryOrderIndexModifyDto.CategoryOrderIndexesDto(categoryId11, nextOrderIndex11));
        CategoryOrderIndexModifyDto categoryOrderIndexModifyDto = new CategoryOrderIndexModifyDto(orderIndexes);
        Executable e = () -> categoryCRUDService.modifyOrderIndexes(categoryOrderIndexModifyDto);

        // then
        Assertions.assertDoesNotThrow(e);
        List<CategoryDto> categories = categoryCRUDService.findCategoryByToken().getCategories();
        categories.forEach(c -> {
            if(c.getId().equals(categoryId1)) {
                Assertions.assertEquals(nextOrderIndex1, c.getOrderIndex());
            } else if (c.getId().equals(categoryId11)) {
                Assertions.assertEquals(nextOrderIndex11, c.getOrderIndex());
            }
        });
        //////////

        ////////// 다른 유저의 리소스에 접근하는 경우
        // when
        login(testAuth2);
        // then
        Assertions.assertThrows(NotYourCategoryException.class, e);
        //////////

        ////////// 존재하지 않는 리소스에 접근하는 경우
        // when
        orderIndexes.removeIf((index) -> true);
        orderIndexes.add(new CategoryOrderIndexModifyDto.CategoryOrderIndexesDto(999L, 9999));
        Assertions.assertThrows(CategoryNotFoundException.class, e);
        // then
        //////////
    }

    @Test
    void removeCategory() {
        // given
        String name = "category_name";
        String color = "#123123";
        Integer orderIndex = 1;
        Long categoryId1 = categoryCRUDService.addCategory(CategorySaveRequestDto.builder().name(name).color(color).orderIndex(orderIndex).build()).getId();
        Long categoryId2 = categoryCRUDService.addCategory(CategorySaveRequestDto.builder().name(name).color(color).orderIndex(orderIndex).build()).getId();

        // when
        categoryCRUDService.removeCategory(categoryId1);

        // then
        Assertions.assertThrows(CategoryNotFoundException.class, () -> categoryCRUDService.removeCategory(categoryId1));
        Assertions.assertDoesNotThrow(() -> categoryCRUDService.removeCategory(categoryId2));
    }

    @Test
    void removeCategory_NotYourCategoryException() {
        // given
        String name = "category_name";
        String color = "#123123";
        Integer orderIndex = 1;
        Long categoryId1 = categoryCRUDService.addCategory(CategorySaveRequestDto.builder().name(name).color(color).orderIndex(orderIndex).build()).getId();
        login(testAuth2);

        // then
        Assertions.assertThrows(NotYourCategoryException.class, () -> categoryCRUDService.removeCategory(categoryId1));
    }

    @Test
    void removeCategory_CategoryNotFoundException() {
        // then
        Assertions.assertThrows(CategoryNotFoundException.class, () -> categoryCRUDService.removeCategory(9999L));
    }

    @Test
    void endCategory() {
        // given
        String name = "category_name";
        String color = "#123123";
        Integer orderIndex = 1;
        Long categoryId1 = categoryCRUDService.addCategory(CategorySaveRequestDto.builder().name(name).color(color).orderIndex(orderIndex).build()).getId();

        // when
        categoryCRUDService.endCategory(categoryId1);
        CategoryDto categoryDto = categoryCRUDService.findCategoryByToken().getCategories().get(0);

        // then
        Assertions.assertEquals(true, categoryDto.isEnded());
    }

    @Test
    void endCategory_CategoryNotFoundException() {
        Assertions.assertThrows(CategoryNotFoundException.class, () -> categoryCRUDService.endCategory(999L));
    }

    @Test
    void endCategory_NotYourCategoryException() {
        // given
        String name = "category_name";
        String color = "#123123";
        Integer orderIndex = 1;
        Long categoryId1 = categoryCRUDService.addCategory(CategorySaveRequestDto.builder().name(name).color(color).orderIndex(orderIndex).build()).getId();
        login(testAuth2);

        // then
        Assertions.assertThrows(NotYourCategoryException.class, () -> categoryCRUDService.endCategory(categoryId1));
    }
}