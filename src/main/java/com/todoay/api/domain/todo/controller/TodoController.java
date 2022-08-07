package com.todoay.api.domain.todo.controller;

import com.todoay.api.domain.todo.dto.DailyTodoSaveRequestDto;
import com.todoay.api.domain.todo.dto.DailyTodoSaveResponseDto;
import com.todoay.api.domain.todo.dto.DueDateTodoSaveRequestDto;
import com.todoay.api.domain.todo.dto.DueDateTodoSaveResponseDto;
import com.todoay.api.domain.todo.service.DailyTodoCRUDService;
import com.todoay.api.domain.todo.service.DueDateTodoCRUDService;
import com.todoay.api.domain.todo.service.TodoService;
import com.todoay.api.global.exception.ErrorResponse;
import com.todoay.api.global.exception.ValidErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;
    private final DailyTodoCRUDService dailyTodoCRUDService;
    private final DueDateTodoCRUDService dueDateTodoCRUDService;

    @PostMapping("/daily")
    @Operation(
            summary = "DailyTodo를 추가한다.",
            responses = {
                    @ApiResponse(responseCode = "201"),  // 요청이 수용되어 리소스가 만들어졌을 때
                    @ApiResponse(responseCode = "400", description = "올바른 양식을 입력하지 않음.", content = @Content(schema = @Schema(implementation = ValidErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Access Token 만료", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<DailyTodoSaveResponseDto> dailyTodoSave(@RequestBody @Validated DailyTodoSaveRequestDto dailyTodoSaveRequestDto) {
        DailyTodoSaveResponseDto dailyTodoSaveResponseDto = dailyTodoCRUDService.addTodo(dailyTodoSaveRequestDto);
        return ResponseEntity.ok(dailyTodoSaveResponseDto);
    }



    @PostMapping("/duedate")
    @Operation(
            summary = "DuedateTodo를 추가한다.",
            responses = {
                    @ApiResponse(responseCode = "201"),  // 요청이 수용되어 리소스가 만들어졌을 때
                    @ApiResponse(responseCode = "400", description = "올바른 양식을 입력하지 않음.", content = @Content(schema = @Schema(implementation = ValidErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Access Token 만료", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<DueDateTodoSaveResponseDto> dueDateTodoSave(@RequestBody @Validated DueDateTodoSaveRequestDto dueDateTodoSaveRequestDto) {
        DueDateTodoSaveResponseDto dueDateTodoSaveResponseDto = dueDateTodoCRUDService.addTodo(dueDateTodoSaveRequestDto);
        return ResponseEntity.ok(dueDateTodoSaveResponseDto);
    }

}
