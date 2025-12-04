package com.practice.RestApi.Modules.User.Dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequestDto {
    @Size(min = 2, message = "At least 2 character is mandatory in name")
    private String name;

    @Email(message = "Please enter a valid email")
    private String email;
}
