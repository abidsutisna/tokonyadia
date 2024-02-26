package com.enigma.tokonyadia.securiry;

import java.security.SignatureException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.tokonyadia.dto.response.JwtClaim;
import com.enigma.tokonyadia.models.entity.UserCredential;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Service
public class JwtUtils {
    
    @Value("${jwt.secretKey}")
    private String secretKey;
    
    @Value("${jwt.expiretionInSecond}")
    private Long expiretionInSecond;

    @Value("${jwt.appName}")
    private String appName;
    
    public String generateToken(UserCredential user) {
        try {
            List<String> roles = user.getRoles().stream().map(role -> role.getRole().name()).toList();
            return JWT.create()
                    .withIssuer(appName)
                    .withSubject(user.getId())
                    .withExpiresAt(Instant.now().plusSeconds(expiretionInSecond))
                    .withClaim("roles", roles)
                    .sign(Algorithm.HMAC512(secretKey));
        } catch (JWTCreationException e) {
            log.error("Error while creating JWT token", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    
    
    public boolean verifyJwtToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getIssuer().equals(appName);
        } catch (JWTVerificationException e){
            log.error("Invalid JWT signature: {}", e.getMessage());
            return false;
        } 
    }
    
    public JwtClaim getUserInfoByToken (String token){
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
            return JwtClaim.builder()
                    .userId(decodedJWT.getSubject())
                    .roles(roles)
                    .build();
        } catch (JWTVerificationException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
    
}
