package com.todoay.api.domain.category.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.category.dto.*;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.exception.CategoryNotFoundException;
import com.todoay.api.domain.category.exception.NotYourCategoryException;
import com.todoay.api.domain.category.repository.CategoryRepository;
import com.todoay.api.global.context.LoginAuthContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryCRUDServiceImpl implements CategoryCRUDService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategorySaveResponseDto addCategory(CategorySaveRequestDto dto) {
        Auth auth = LoginAuthContext.getLoginAuth();
        Category category = categoryRepository.save(
                Category.builder()
                        .name(dto.getName())
                        .color(dto.getColor())
                        .orderIndex(dto.getOrderIndex())
                        .auth(auth)
                        .build()
        );
        return CategorySaveResponseDto.builder().id(category.getId()).build();
    }

    @Override
    public void modifyCategory(Long id, CategoryModifyRequestDto dto) {
        Category category = checkIsPresentAndIsMineAndGetCategory(id);
        category.modify(dto.getName(), dto.getColor());
    }

    @Override
    public CategoryListByTokenResponseDto findCategoryByToken() {
        List<Category> categories = categoryRepository.findCategoryByAuth(LoginAuthContext.getLoginAuth());
        return CategoryListByTokenResponseDto.of(categories);
    }

    @Override
    public void modifyOrderIndexes(CategoryOrderIndexModifyDto dto) {
        List<CategoryOrderIndexModifyDto.CategoryOrderIndexesDto> indexes = dto.getOrderIndexes();
        indexes.forEach(i -> {
            Category category = checkIsPresentAndIsMineAndGetCategory(i.getId());
            category.changeOrderIndex(i.getOrderIndex());
        });
    }

    @Override
    public void removeCategory(Long id) {
        Category category = checkIsPresentAndIsMineAndGetCategory(id);
        categoryRepository.delete(category);
    }

    @Override
    public void endCategory(Long id) {
        Category category = checkIsPresentAndIsMineAndGetCategory(id);
        category.end();
    }

    private Category checkIsPresentAndIsMineAndGetCategory(Long id) {
        Category category = checkIsPresentAndGetCategory(id);
        checkIsMine(category);
        return category;
    }

    private void checkIsMine(Category category) {
        if(!category.getAuth().equals(LoginAuthContext.getLoginAuth())) throw new NotYourCategoryException();
    }

    private Category checkIsPresentAndGetCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
    }
}
