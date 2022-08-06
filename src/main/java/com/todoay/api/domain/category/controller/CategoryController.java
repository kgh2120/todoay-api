package com.todoay.api.domain.category.controller;


import com.todoay.api.domain.category.dto.CategoryListByTokenResponseDto;
import com.todoay.api.domain.category.dto.CategoryModifyRequestDto;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CategorySaveResponseDto> categorySave(@RequestBody @Validated CategorySaveRequestDto categorySaveRequestDto) {
        CategorySaveResponseDto categorySaveResponseDto = categoryCRUDService.addCategory(categorySaveRequestDto);
        return ResponseEntity.status(201).body(categorySaveResponseDto);
    }

    @GetMapping("/my")
    public ResponseEntity<CategoryListByTokenResponseDto> categoryListByToken() {
        CategoryListByTokenResponseDto categories = categoryCRUDService.findCategoryByToken();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("")
    @Operation(
            summary = "로그인 유저의 카테고리를 수정한다.",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "400", description = "올바른 양식을 입력하지 않음.", content = @Content(schema = @Schema(implementation = ValidErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Access Token 만료", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "카테고리가 로그인 유저의 것이 아님", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            }
    )
    public ResponseEntity<Void> categoryModify(@RequestBody @Validated CategoryModifyRequestDto categoryModifyRequestDto) {
        System.out.println(categoryModifyRequestDto);
        categoryCRUDService.modifyCategory(categoryModifyRequestDto);
        return ResponseEntity.noContent().build();
    }
}
