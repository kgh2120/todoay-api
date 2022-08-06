package com.todoay.api.domain.category.dto;

import com.todoay.api.global.customValidation.annotation.ValidationCategoryColor;
import com.todoay.api.global.customValidation.annotation.ValidationCategoryName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryModifyRequestDto {
    @ValidationCategoryName
    private String name;

    @ValidationCategoryColor
    private String color;
}
