package com.practice.RestApi.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.RestApi.Entity.User;
import com.practice.RestApi.Repository.UserRepository;
import com.practice.RestApi.Security.JwtUtil;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    
    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public JwtUtil jwtUtil;

    public Map<String, Object> signup(String email, String password){

       if(userRepository.existsByEmail(email)){
        throw new RuntimeException("Eamil is already present");
       }

       User user = new User();
       user.setEmail(email);
       user.setPassword(passwordEncoder.encode(password));

       userRepository.save(user);

       String token = jwtUtil.generateJwtToken(email, user.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User Registered Successfully");
        response.put("email", email);
        response.put("token", token);

        return response;
    }

    public Map<String, Object> login(String email, String password) {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password");
        }

            String userSavedPassword = user.getPassword();
            Boolean isPasswordCorrect = passwordEncoder.matches(password, userSavedPassword);
            System.out.println("isPasswordCorrect " + isPasswordCorrect);

            if (!isPasswordCorrect) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect Password");
            }

            String generatedJwt = jwtUtil.generateJwtToken(email, user.getId());
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User Login Successfully");
            response.put("token", generatedJwt);
            response.put("email", email);
            return response;



    }

}
