package com.todoay.api.domain.category.controller;


import com.todoay.api.domain.category.dto.CategorySaveRequestDto;
import com.todoay.api.domain.category.dto.CategorySaveResponseDto;
import com.todoay.api.domain.category.service.CategoryCRUDService;
import com.todoay.api.domain.category.service.CategoryService;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.ValidErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryCRUDService categoryCRUDService;

    @PostMapping("")
    @Operation(
            summary = "로그인 유저의 카테고리를 추가한다.",
            responses = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400", description = "올바른 양식을 입력하지 않음.", content = @Content(schema = @Schema(implementation = ValidErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Access Token 만료", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<CategorySaveResponseDto> categorySave(@RequestBody CategorySaveRequestDto categorySaveRequestDto) {
        CategorySaveResponseDto categorySaveResponseDto = categoryCRUDService.addCategory(categorySaveRequestDto);
        return ResponseEntity.ok(categorySaveResponseDto);
    }
}
