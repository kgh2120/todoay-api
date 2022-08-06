package com.todoay.api.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
public class CategoryOrderIndexModifyDto {
    @NotNull
    private List<CategoryOrderIndexesDto> orderIndexes;

    @Data
    @AllArgsConstructor
    public static class CategoryOrderIndexesDto {
        @NotNull
        private Long id;
        @NotNull
        private Integer orderIndex;
    }
}
