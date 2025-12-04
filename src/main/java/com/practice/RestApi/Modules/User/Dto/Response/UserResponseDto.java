package com.practice.RestApi.Modules.User.Dto.Response;

import com.practice.RestApi.Modules.User.Entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {

    private Long id;

    private String name;

    private String email;

    private String phone;

    private User.Role role;

    private Boolean isActive;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
