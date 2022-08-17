package com.todoay.api.domain.todo.dto;

import com.todoay.api.domain.category.entity.Category;
import lombok.Data;

@Data
public class CategoryInfoDto {
    private String name;
    private String color;

    public static CategoryInfoDto create(Category category) {
        CategoryInfoDto dto = new CategoryInfoDto();
        dto.name = category.getName();
        dto.color = category.getColor();
        return dto;
    }
}
