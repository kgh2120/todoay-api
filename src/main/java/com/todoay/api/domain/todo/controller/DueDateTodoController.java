package com.todoay.api.domain.todo.controller;

import com.todoay.api.domain.todo.dto.daily.DailyTodoSaveResponseDto;
import com.todoay.api.domain.todo.dto.duedate.*;
import com.todoay.api.domain.todo.service.DueDateTodoCRUDService;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.ValidErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/todo/due-date")
@RequiredArgsConstructor
@RestController
public class DueDateTodoController {
    private final DueDateTodoCRUDService dueDateTodoCRUDService;

    @PostMapping
    @Operation(
            summary = "DuedateTodo를 추가한다.",
            responses = {
                    @ApiResponse(responseCode = "201"),  // 요청이 수용되어 리소스가 만들어졌을 때
                    @ApiResponse(responseCode = "400", description = "올바른 양식을 입력하지 않음.", content = @Content(schema = @Schema(implementation = ValidErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Access Token 만료", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "올바르지 않은 ENUM값이 들어왔습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<DueDateTodoSaveResponseDto> dueDateTodoSave(@RequestBody @Validated DueDateTodoSaveRequestDto dueDateTodoSaveRequestDto) {
        DueDateTodoSaveResponseDto dueDateTodoSaveResponseDto = dueDateTodoCRUDService.addTodo(dueDateTodoSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dueDateTodoSaveResponseDto);
    }

    @Operation(
            summary = "조건에 맞는 내 due-date Todo를 정렬해서 본다.",
            description = "마감일, 중요도 중에서 선택해서 Due-Date TODO를 정렬하여 확인한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = DueDateTodoReadResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Access Token 만료", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "올바르지 않은 ENUM값이 들어왔습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/my")
    public ResponseEntity<List<DueDateTodoReadResponseDto>> readDueDateTodoOrderByQueryString(@RequestParam("order") String order) {
        List<DueDateTodoReadResponseDto> dtos = dueDateTodoCRUDService.readTodosOrderByCondition(order);
        return ResponseEntity.ok(dtos);
    }
    @Operation(
            summary = "Due-Date Todo를 디테일하게 본다.",
            description = "ID에 해당하는 Due-Date Todo의 상세정보를 조회한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = DueDateTodoReadDetailResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Access Token 만료", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "해당 id의 리소스가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/my/{id}")
    public ResponseEntity<DueDateTodoReadDetailResponseDto> readDueDateTodoDetails(@PathVariable("id")Long id) {
        DueDateTodoReadDetailResponseDto dto = dueDateTodoCRUDService.readDueDateTodoDetail(id);
        return ResponseEntity.ok(dto);
    }
    @Operation(
            summary = "완료한 Due-Date Todo를 조회한다.",
            description = "완료한 Due-Date Todo를 조회한다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = DueDateTodoReadResponseDto.class))),
                    @ApiResponse(responseCode = "401", description = "Access Token 만료", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @GetMapping("/my/finished")
    public ResponseEntity<List<DueDateTodoReadResponseDto>> readFinishedDueDateTodo() {
        List<DueDateTodoReadResponseDto> dtos = dueDateTodoCRUDService.readFinishedTodos();
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "로그인 유저의 due-dateTodo를 수정한다.",
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "400", description = "올바른 양식을 입력하지 않음.", content = @Content(schema = @Schema(implementation = ValidErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Access Token 만료", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "1. Todo가 로그인 유저의 것이 아님 \t\n 2. 올바르지 않은 ENUM값이 들어왔습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "해당 id의 리소스가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<DailyTodoSaveResponseDto> modifyDueDateTodo(@RequestBody @Validated DueDateTodoModifyRequestDto dueDateTodoModifyRequestDto, @PathVariable Long id) {
        dueDateTodoCRUDService.modifyDueDateTodo(id, dueDateTodoModifyRequestDto);
        return ResponseEntity.noContent().build();
    }
}
