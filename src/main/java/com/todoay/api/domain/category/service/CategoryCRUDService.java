package com.todoay.api.domain.category.service;

import com.todoay.api.domain.category.dto.*;

public interface CategoryCRUDService {
    CategorySaveResponseDto addCategory(CategorySaveRequestDto dto);

    void modifyCategory(Long id, CategoryModifyRequestDto dto);

    CategoryListByTokenResponseDto findCategoryByToken();

    void modifyOrderIndexes(CategoryOrderIndexModifyDto dto);
}
