package com.practice.RestApi.Modules.User.Dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequestDto {

    @NotBlank(message = "Name is required")
    @Size(min = 2, message = "Atleast 2 character required in name")
    private String name;

//    @NotBlank(message = "email is required")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Password is Mandatory")
    private String password;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian phone number")
    private String phone;

}
