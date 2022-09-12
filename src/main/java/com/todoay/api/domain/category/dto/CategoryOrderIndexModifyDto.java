package com.todoay.api.domain.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CategoryOrderIndexModifyDto {
    @NotNull
    private List<CategoryOrderIndexesDto> orderIndexes;

    @Data
    @AllArgsConstructor  @NoArgsConstructor
    public static class CategoryOrderIndexesDto {
        @NotNull
        private Long id;
        @NotNull
        private Integer orderIndex;
    }
}
