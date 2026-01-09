package com.practice.RestApi.Security;

import java.util.Date;

import com.practice.RestApi.Modules.User.Entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret:springpractice}")
    private String jwSecret;

    @Value("${app.jwt.expiration-ms:86400000}")
    private Long jwExpirationMs;

    public String generateJwtToken(String email, Long id, User.Role role){

        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwExpirationMs);

        String signedJwt = JWT.create()
                .withSubject(email)
                .withClaim("userId", id)
                .withClaim("role", role.name())
                .withIssuedAt(now)
                .withExpiresAt(expiry)
                .sign(Algorithm.HMAC256(jwSecret));

        return signedJwt;
    }

    public Boolean validateJwtToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwSecret);
            JWT.require(algorithm).build().verify(token);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
