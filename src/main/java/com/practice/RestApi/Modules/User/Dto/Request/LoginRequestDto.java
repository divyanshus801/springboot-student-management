package com.practice.RestApi.Modules.User.Dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequestDto {

    @Email(message = "Enter a valid email")
    private String email;

    @NotBlank(message = "Password is Mandatory")
    private String password;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid phone number")
    private String phone;
}
