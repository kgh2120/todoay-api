package com.todoay.api.domain.todo.dto.daily;

import com.todoay.api.domain.category.entity.Category;
import lombok.Data;

@Data
public class CategoryInfoDto {

    private Long id;
    private String name;
    private String color;

    public static CategoryInfoDto create(Category category) {
        CategoryInfoDto dto = new CategoryInfoDto();
        dto.id = category.getId();
        dto.name = category.getName();
        dto.color = category.getColor();
        return dto;
    }
}
