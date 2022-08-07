package com.todoay.api.domain.category.dto;

import com.todoay.api.global.customValidation.annotation.ValidationCategoryColor;
import com.todoay.api.global.customValidation.annotation.ValidationCategoryName;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class CategorySaveRequestDto {
    @ValidationCategoryName
    private String name;

    @ValidationCategoryColor
    private String color;

    @NotNull
    private Integer orderIndex;
}
