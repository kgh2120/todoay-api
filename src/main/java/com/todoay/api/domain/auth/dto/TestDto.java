package com.todoay.api.domain.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data @NoArgsConstructor
public class TestDto {

    @ApiModelProperty(example = "400")
    private String code;

    @ApiModelProperty(example = "badRequest")
    private String message;

}
