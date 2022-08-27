package com.todoay.api.domain.todo.controller;

import com.todoay.api.domain.todo.service.TodoService;
import com.todoay.api.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

//        @DeleteMapping("/{id}")
//    @Operation(
//            summary = "로그인 유저의 Todo를 삭제한다.",
//            responses = {
//                    @ApiResponse(responseCode = "201"),  // 요청이 수용되어 리소스가 만들어졌을 때
//                    @ApiResponse(responseCode = "403", description = "Todo가 로그인 유저의 것이 아님", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//                    @ApiResponse(responseCode = "404", description = "해당 id의 리소스가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
//            }
//    )
//    public ResponseEntity<DailyTodoSaveResponseDto> dailyTodoDelete(@PathVariable Long id) {
//        dailyTodoCRUDService.deleteDailyTodo(id);
//        return ResponseEntity.noContent().build();
//    }





//    @DeleteMapping("/{id}")
//    @Operation(
//            summary = "로그인 유저의 DailyTodo를 삭제한다.",
//            responses = {
//                    @ApiResponse(responseCode = "201"),  // 요청이 수용되어 리소스가 만들어졌을 때
//                    @ApiResponse(responseCode = "403", description = "Todo가 로그인 유저의 것이 아님", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//                    @ApiResponse(responseCode = "404", description = "해당 id의 리소스가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
//            }
//    )
//    public ResponseEntity<DailyTodoSaveResponseDto> dailyTodoDelete(@PathVariable Long id) {
//        dailyTodoCRUDService.deleteDailyTodo(id);
//        return ResponseEntity.noContent().build();
//    }
//
//
//
//
//
//    @DeleteMapping("/due-date/{id}")
//    @Operation(
//            summary = "로그인 유저의 DailyTodo를 삭제한다.",
//            responses = {
//                    @ApiResponse(responseCode = "201"),  // 요청이 수용되어 리소스가 만들어졌을 때
//                    @ApiResponse(responseCode = "401", description = "Access Token 만료", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//                    @ApiResponse(responseCode = "403", description = "Todo가 로그인 유저의 것이 아님", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//                    @ApiResponse(responseCode = "404", description = "해당 id의 리소스가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
//            }
//    )
//    public ResponseEntity<DueDateTodoSaveResponseDto> deleteDueDateTodo(@PathVariable Long id) {
//        dueDateTodoCRUDService.deleteDueDateTodo(id);
//        return ResponseEntity.noContent().build();
//    }

    @Operation(
            summary = "Todo의 완료 상태를 반대 상태로 바꾼다.",
            description = "pathvariable로 전달받은 ID를 통해 Todo를 얻고, 완료 상태를 반대로 바꾼다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "전환 성공"),
                    @ApiResponse(responseCode = "401", description = "Access Token 만료", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "403", description = "Todo가 로그인 유저의 것이 아님", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "해당 id의 리소스가 존재하지 않음", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    @PatchMapping("/{id}/switch")
    public ResponseEntity<Void> switchTodoFinishState(@PathVariable("id") Long id) {
        todoService.switchFinishState(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
