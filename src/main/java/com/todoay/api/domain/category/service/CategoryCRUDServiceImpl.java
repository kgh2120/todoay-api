package com.todoay.api.domain.category.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.category.dto.*;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.exception.CategoryNotFoundException;
import com.todoay.api.domain.category.exception.NotYourCategoryException;
import com.todoay.api.domain.category.repository.CategoryRepository;
import com.todoay.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryCRUDServiceImpl implements CategoryCRUDService {
    private final CategoryRepository categoryRepository;
    private final AuthRepository authRepository;

    private final JwtProvider jwtProvider;

    @Override
    public CategorySaveResponseDto addCategory(CategorySaveRequestDto dto) {
        Auth auth = authRepository.findByEmail(jwtProvider.getLoginId()).get();
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
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        if(!jwtProvider.getLoginId().equals(category.getAuth().getEmail())) throw new NotYourCategoryException();
        category.modify(dto.getName(), dto.getColor());
    }

    @Override
    public CategoryListByTokenResponseDto findCategoryByToken() {
        List<Category> categories = categoryRepository.findCategoryByAuth_Email(jwtProvider.getLoginId());
        return CategoryListByTokenResponseDto.of(categories);
    }

    @Override
    public void modifyOrderIndexes(CategoryOrderIndexModifyDto dto) {
        String loginEmail = jwtProvider.getLoginId();
        List<CategoryOrderIndexModifyDto.CategoryOrderIndexesDto> indexes = dto.getOrderIndexes();
        indexes.forEach(i -> {
            Category category = categoryRepository.findById(i.getId()).orElseThrow(CategoryNotFoundException::new);
            if(!category.getAuth().getEmail().equals(loginEmail)) throw new NotYourCategoryException();
            category.changeOrderIndex(i.getOrderIndex());
        });
    }

    @Override
    public void removeCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
        if(!category.getAuth().getEmail().equals(jwtProvider.getLoginId())) throw new NotYourCategoryException();
        categoryRepository.delete(category);
    }
}
