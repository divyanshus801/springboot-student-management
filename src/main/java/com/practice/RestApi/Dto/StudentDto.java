package com.practice.RestApi.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDto {

    // @NotBlank(message = "Id is Mandatory")
    private Long id;

    @NotBlank(message = "Name is Mandatory")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotBlank(message = "Email is Mandatory")
    @Email(message = "Email must be valid")
    private String email;

}
