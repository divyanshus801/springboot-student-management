package com.practice.RestApi.Modules.User.Dto.Response;

import lombok.Data;

@Data
public class LoginResponseDto {

    private String token;

    private String message;

    private String status;

    private UserResponseDto user;
}
