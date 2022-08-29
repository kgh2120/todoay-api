package com.todoay.api.domain.category.dto;

import com.todoay.api.global.customValidation.annotation.ValidationCategoryColor;
import com.todoay.api.global.customValidation.annotation.ValidationCategoryName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class CategorySaveRequestDto {
    @ValidationCategoryName
    private String name;

    @ValidationCategoryColor
    private String color;

    @NotNull
    private Integer orderIndex;
}
