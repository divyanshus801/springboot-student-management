package com.practice.RestApi.Security;

import com.auth0.jwt.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.User;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getRequestURI();
        
        // Skip JWT validation for public endpoints
        if (requestPath.equals("/signup") || requestPath.equals("/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token  = authHeader.substring(7);

        try {
            boolean isValid = jwtUtil.validateJwtToken(token);

            if(!isValid){
                throw new RuntimeException("Token invalid");
            }

            String email = JWT.decode(token).getSubject();
            Long userId = JWT.decode(token).getClaim("userId").asLong();
            String role = JWT.decode(token).getClaim("role").asString();

            System.out.println("57"+role);

            String AuthorityName = "ROLE_" + role;
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(AuthorityName);

            User userDetails = new User(email, "", Collections.singleton(authority));
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authToken.setDetails(userId);

            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (Exception e){
            //Invalid -- token => clear context
            SecurityContextHolder.clearContext();

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            response.getWriter().write("{\"status\":\"error\", \"message\":\"Invalid or expired JWT token\"}");
            response.flushBuffer();
            return;
        }
        filterChain.doFilter(request, response);

    }
}
