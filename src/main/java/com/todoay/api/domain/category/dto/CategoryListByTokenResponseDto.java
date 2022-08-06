package com.todoay.api.domain.category.dto;

import com.todoay.api.domain.category.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryListByTokenResponseDto {
    private List<CategoryDto> categories = new ArrayList<>();

    public static CategoryListByTokenResponseDto of(List<Category> list) {
        CategoryListByTokenResponseDto dto = new CategoryListByTokenResponseDto();
        List<CategoryDto> categories = dto.getCategories();
        list.forEach(i -> categories.add(CategoryDto.of(i)));
        return dto;
    }

    @Data
    @AllArgsConstructor
    public static
    class CategoryDto {
        private String name;
        private String color;
        private Integer orderIndex;
        private boolean isEnded;

        public static CategoryDto of(Category category) {
            return new CategoryDto(category.getName(), category.getColor(), category.getOrderIndex(), category.isEnded());
        }
    }
}
