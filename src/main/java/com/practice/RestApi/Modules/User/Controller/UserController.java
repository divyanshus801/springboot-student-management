package com.practice.RestApi.Modules.User.Controller;

import com.practice.RestApi.Modules.User.Dto.Request.LoginRequestDto;
import com.practice.RestApi.Modules.User.Dto.Request.SignupRequestDto;
import com.practice.RestApi.Modules.User.Dto.Request.UpdateProfileRequestDto;
import com.practice.RestApi.Modules.User.Dto.Response.LoginResponseDto;
import com.practice.RestApi.Modules.User.Dto.Response.SignUpResponseDto;
import com.practice.RestApi.Modules.User.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return ResponseEntity.ok(userService.signup(signupRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    @PutMapping("/updateProfile")
    public ResponseEntity<SignUpResponseDto> updateProfile(@Valid @RequestBody UpdateProfileRequestDto updateProfileRequestDto) {
        return ResponseEntity.ok(userService.updateProfile(updateProfileRequestDto));
    }


    //Todo add update role controller for admin user

}
