package com.example.springsecurityjwt.services.singInService;

import com.example.springsecurityjwt.models.RegistrationAndSingInRequestModel;
import com.example.springsecurityjwt.models.ResponseModel;

public interface SingInService {
    ResponseModel authenticate(RegistrationAndSingInRequestModel authenticateModel);
}
