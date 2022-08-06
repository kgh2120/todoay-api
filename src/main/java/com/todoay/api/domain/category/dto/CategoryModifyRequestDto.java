package com.todoay.api.domain.category.dto;

import com.todoay.api.global.customValidation.annotation.ValidationCategoryColor;
import com.todoay.api.global.customValidation.annotation.ValidationCategoryName;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CategoryModifyRequestDto {
    @NotNull
    private Long id;
    @ValidationCategoryName
    private String name;
    @ValidationCategoryColor
    private String color;
}
