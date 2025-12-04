package com.practice.RestApi.Modules.User.Service;

import com.practice.RestApi.Modules.User.Dto.Request.LoginRequestDto;
import com.practice.RestApi.Modules.User.Dto.Request.SignupRequestDto;
import com.practice.RestApi.Modules.User.Dto.Request.UpdateProfileRequestDto;
import com.practice.RestApi.Modules.User.Dto.Response.LoginResponseDto;
import com.practice.RestApi.Modules.User.Dto.Response.SignUpResponseDto;
import com.practice.RestApi.Modules.User.Dto.Response.UserResponseDto;
import com.practice.RestApi.Modules.User.Entity.User;
import com.practice.RestApi.Modules.User.Repository.UserRepository;
import com.practice.RestApi.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    
    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public SignUpResponseDto signup(SignupRequestDto signupRequestDto) {

        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();
        String phone = signupRequestDto.getPhone();
        String name = signupRequestDto.getName();

        if(((email == null || email.isBlank()) && (phone == null || phone.isBlank()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or phone is required");
        }

       if(email != null && userRepository.existsByEmail(email)){
        throw new RuntimeException("Eamil is already present");
       }

       User user = new User();
       user.setEmail(email);
       user.setPassword(passwordEncoder.encode(password));
       user.setPhone(phone);
       user.setName(name);


       User savedUser = userRepository.save(user);
       UserResponseDto userResponseDto = new UserResponseDto();
       userResponseDto.setId(savedUser.getId());
       userResponseDto.setEmail(savedUser.getEmail());
       userResponseDto.setName(savedUser.getName());
       userResponseDto.setPhone(savedUser.getPhone());
       userResponseDto.setRole(savedUser.getRole());
       userResponseDto.setIsActive(savedUser.getIsActive());
       userResponseDto.setCreatedAt(savedUser.getCreatedAt());
       userResponseDto.setUpdatedAt(savedUser.getUpdatedAt());

      SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
      signUpResponseDto.setUser(userResponseDto);
      signUpResponseDto.setMessage("User registered successfully");
      signUpResponseDto.setStatus("success");
      return signUpResponseDto;

    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        String phone = loginRequestDto.getPhone();

        if(((email == null || email.isBlank()) && (password == null || password.isBlank()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or phone is required");
        }

        User user = userRepository.findByEmail(email);
        System.out.println("userDeatis in service"+ user.getId());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
        }

            String userSavedPassword = user.getPassword();
            Boolean isPasswordCorrect = passwordEncoder.matches(password, userSavedPassword);


            if (!isPasswordCorrect) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
            }

            System.out.println("srvice userId"+user.getId());

            String generatedJwt = jwtUtil.generateJwtToken(email, user.getId());

            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setId(user.getId());
            userResponseDto.setEmail(email);
            userResponseDto.setName(user.getName());
            userResponseDto.setPhone(user.getPhone());
            userResponseDto.setRole(user.getRole());
            userResponseDto.setIsActive(user.getIsActive());
            userResponseDto.setCreatedAt(user.getCreatedAt());
            userResponseDto.setUpdatedAt(user.getUpdatedAt());

            LoginResponseDto loginResponseDto = new LoginResponseDto();
            loginResponseDto.setUser(userResponseDto);
            loginResponseDto.setMessage("User logged successfully");
            loginResponseDto.setStatus("success");
            loginResponseDto.setToken(generatedJwt);
            return loginResponseDto;

    }

    public SignUpResponseDto updateProfile(UpdateProfileRequestDto updateProfileRequestDto){


        try {
            String name = updateProfileRequestDto.getName();
            String email = updateProfileRequestDto.getEmail();

            // Get userId from the details (set in JwtAuthenticationFilter)
            Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();


            User user = userRepository.findById(userId).orElseThrow(() -> {

                return new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            });

            if (name != null && !name.isBlank()) {
                user.setName(name);
                logger.info("Updated name to: {}", name);
            }

            if (email != null && !email.equals(user.getEmail())) {
                if (userRepository.existsByEmail(email)) {

                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already in use");
                }
                user.setEmail(email);

            }

            User updatedUser = userRepository.save(user);


            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setId(updatedUser.getId());
            userResponseDto.setEmail(updatedUser.getEmail());
            userResponseDto.setName(updatedUser.getName());
            userResponseDto.setPhone(updatedUser.getPhone());
            userResponseDto.setRole(updatedUser.getRole());
            userResponseDto.setIsActive(updatedUser.getIsActive());
            userResponseDto.setCreatedAt(updatedUser.getCreatedAt());
            userResponseDto.setUpdatedAt(updatedUser.getUpdatedAt());

            SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
            signUpResponseDto.setUser(userResponseDto);
            signUpResponseDto.setMessage("User updated successfully");
            signUpResponseDto.setStatus("success");

            return signUpResponseDto;
        }
        catch (Exception e){
            logger.error("Error in updateProfile: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

}
