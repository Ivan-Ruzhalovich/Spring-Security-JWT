package com.example.springsecurityjwt.services.authService;

import com.example.springsecurityjwt.models.RegistrationAndSingInRequestModel;
import com.example.springsecurityjwt.models.ResponseModel;

public interface AuthService {
    ResponseModel registration(RegistrationAndSingInRequestModel registerRequestModel);
    ResponseModel singIn(RegistrationAndSingInRequestModel singInRequestModel);
}
