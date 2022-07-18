package com.todoay.api.domain.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Test200Dto {
    @ApiModelProperty(example = "200")
    private String code;
    @ApiModelProperty(example = "참 잘했어요")
    private String message;
}
