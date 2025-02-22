package com.example.springsecurityjwt.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.function.Function;

public interface JWTUtils {
    String generateToken(UserDetails userDetails);
    String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails);
    String extractUserName (String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    boolean isTokenExpiration(String token);
}
