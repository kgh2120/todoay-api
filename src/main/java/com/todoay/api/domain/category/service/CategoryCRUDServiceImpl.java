package com.todoay.api.domain.category.service;

import com.todoay.api.domain.auth.entity.Auth;
import com.todoay.api.domain.auth.repository.AuthRepository;
import com.todoay.api.domain.category.dto.CategorySaveRequestDto;
import com.todoay.api.domain.category.dto.CategorySaveResponseDto;
import com.todoay.api.domain.category.entity.Category;
import com.todoay.api.domain.category.repository.CategoryRepository;
import com.todoay.api.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryCRUDServiceImpl implements CategoryCRUDService {
    private final CategoryRepository categoryRepository;
    private final AuthRepository authRepository;

    private final JwtProvider jwtProvider;

    @Override
    @Transactional
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
}
