package com.veezy.todoapp.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AdminRequest {
    @NotNull
    private Integer id;
}
