package com.example.springsecurityjwt.services.registrationService;

import com.example.springsecurityjwt.models.RegistrationAndSingInRequestModel;
import com.example.springsecurityjwt.models.ResponseModel;

public interface RegistrationService {
    ResponseModel registration(RegistrationAndSingInRequestModel registerRequestModel);
}
