package com.practice.RestApi.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    
    private Long id;

    private String name;

    private String email;

    private String phone;

}
