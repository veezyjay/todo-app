package com.veezy.todoapp.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {

    @NotBlank(message = "Please provide a username")
    @Size(message = "Username must be between 4 and 20 characters", min = 4, max = 20)
    private String username;

    @NotBlank(message = "Please provide a password")
    @Size(message = "Password must be between 8 and 20 characters", min = 8, max = 20)
    private String password;

    @NotBlank(message = "Please provide a matching password")
    @Size(message = "Password must be between 8 and 20 characters", min = 8, max = 20)
    private String passwordConfirm;

}
