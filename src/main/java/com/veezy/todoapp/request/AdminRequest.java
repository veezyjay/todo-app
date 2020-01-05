package com.veezy.todoapp.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AdminRequest {

    @ApiModelProperty("The user ID")
    @NotNull
    private Integer id;
}
