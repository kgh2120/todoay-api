package com.todoay.api.domain.category.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.category.dto.CategoryModifyRequestDto;
import com.todoay.api.domain.category.dto.CategorySaveRequestDto;
import com.todoay.api.domain.category.dto.CategorySaveResponseDto;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.exception.CategoryNotFoundException;
import com.todoay.api.domain.category.exception.NotYourCategoryException;
import com.todoay.api.domain.category.repository.CategoryRepository;
import com.todoay.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void modifyCategory(CategoryModifyRequestDto dto) {
        Category category = categoryRepository.findById(dto.getId()).orElseThrow(CategoryNotFoundException::new);
        if(!jwtProvider.getLoginId().equals(category.getAuth().getEmail())) throw new NotYourCategoryException();
        category.modify(dto.getName(), dto.getColor());
    }
}
