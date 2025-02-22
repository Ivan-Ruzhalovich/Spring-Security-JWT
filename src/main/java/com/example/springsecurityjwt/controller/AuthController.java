package com.example.springsecurityjwt.controller;

import com.example.springsecurityjwt.models.RefreshRequestModel;
import com.example.springsecurityjwt.models.RegistrationAndSingInRequestModel;
import com.example.springsecurityjwt.models.ResponseModel;
import com.example.springsecurityjwt.services.authService.AuthServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<ResponseModel> register(@RequestBody RegistrationAndSingInRequestModel registerRequestModel){
        return new ResponseEntity<>(service.registration(registerRequestModel), HttpStatus.OK);
    }

    @PostMapping("/singin")
    public ResponseEntity<ResponseModel> singIn (@RequestBody RegistrationAndSingInRequestModel singInRequestModel){
        return new ResponseEntity<>(service.singIn(singInRequestModel),HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseModel> refreshToken(@RequestBody RefreshRequestModel refreshRequestModel){
        return new ResponseEntity<>(service.refreshToken(refreshRequestModel),HttpStatus.OK);
    }
}
