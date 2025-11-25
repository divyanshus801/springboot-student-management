package com.practice.RestApi.Service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.RestApi.Entity.User;
import com.practice.RestApi.Repository.UserRepository;
import com.practice.RestApi.Security.JwtUtil;

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

}
