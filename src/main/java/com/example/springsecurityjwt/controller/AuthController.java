package com.example.springsecurityjwt.controller;


import com.example.springsecurityjwt.models.RequestModel;
import com.example.springsecurityjwt.service.AuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl service;

    public AuthController(AuthServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<RequestModel> register(@RequestBody RequestModel registerRequestModel){
        return new ResponseEntity<>(service.registration(registerRequestModel), HttpStatus.OK);
    }

    @PostMapping("/singin")
    public ResponseEntity<RequestModel> singIn (@RequestBody RequestModel singInRequestModel){
        return new ResponseEntity<>(service.singIn(singInRequestModel),HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<RequestModel> refreshToken(@RequestBody RequestModel refreshTokenRequestModel){
        return new ResponseEntity<>(service.refreshToken(refreshTokenRequestModel),HttpStatus.OK);
    }
}
