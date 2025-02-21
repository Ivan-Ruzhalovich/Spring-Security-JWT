package com.example.springsecurityjwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTUtilsImpl implements JWTUtils{

    private final SecretKey Key;

    private static final long EXPIRATION_TIME = 3_600_000; // 1 hour

    public JWTUtilsImpl() {
        String secretString = "00738e58479a1903711de95f84684dee8fcb4588098957e0b16191e43adacc4f00" +
                "ccede30d1bbd4d50720e8dd0d4193c1d29b914237d143af7bfdb841c54d6cda3b48b0e8fdcf" +
                "910994f23fcea694c0f9f0fd2e51db7c509c68b3bd60bc9b1d0cecfb0403759d1ba9bbe223817" +
                "d0d7df20b68eea50d08418cf788e352203a2d4e4b95b1627373febff432ded3964b502690cc8a683a3" +
                "afe10577b344c8011f93b90e94f79c6dc6d80182203a352ba49f5e214aedf80def9049ef3ac89d410" +
                "c6e0e3f27847519a35b1003919c2e68a70fc5f9709d6bae235ce4ca547896acec98d28147aa0bb6ca703" +
                "d517ed0c50e02e8502439fdc248d8daf7f42710d655789c";
        byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes,"HmacSHA256");
    }

    @Override
    public  String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() +  EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    @Override
    public String generateRefreshToken(HashMap<String, Object> claims,UserDetails userDetails){
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) //24 hours
                .signWith(Key)
                .compact();
    }

    @Override
    public String extractUserName (String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimsTFunction) {
        return claimsTFunction
                .apply(Jwts.parser()
                        .verifyWith(Key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload());
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails){
        return (extractUserName(token).equals(userDetails.getUsername())&&!isTokenExpiration(token));
    }

    @Override
    public boolean isTokenExpiration(String token){
        boolean isTokenExpiration = extractClaims(token, Claims::getExpiration).before(new Date());
        if (isTokenExpiration)
            return extractClaims(token, Claims::getExpiration).before(new Date());
        else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Закончился срок действия токена!");
    }
}
