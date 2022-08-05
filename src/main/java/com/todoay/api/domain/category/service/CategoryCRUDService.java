package com.todoay.api.domain.category.service;

import com.todoay.api.domain.category.dto.CategorySaveRequestDto;
import com.todoay.api.domain.category.dto.CategorySaveResponseDto;

public interface CategoryCRUDService {
    CategorySaveResponseDto addCategory(CategorySaveRequestDto dto);
}
