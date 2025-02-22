package com.example.springsecurityjwt.services.authService;

import com.example.springsecurityjwt.models.RefreshRequestModel;
import com.example.springsecurityjwt.models.RegistrationAndSingInRequestModel;
import com.example.springsecurityjwt.models.ResponseModel;
import com.example.springsecurityjwt.services.refreshTokenService.RefreshTokenServiceImpl;
import com.example.springsecurityjwt.services.registrationService.RegistrationServiceImpl;
import com.example.springsecurityjwt.services.singInService.SingInServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RegistrationServiceImpl registrationService;
    private final SingInServiceImpl singInService;
    private final RefreshTokenServiceImpl refreshTokenService;

    @Override
    public ResponseModel registration(RegistrationAndSingInRequestModel registerRequestModel) {
        return registrationService.registration(registerRequestModel);
    }

    @Override
    public ResponseModel singIn(RegistrationAndSingInRequestModel singInModel) {
        return singInService.authenticate(singInModel);
    }

    public ResponseModel refreshToken(RefreshRequestModel refreshRequestModel) {
        return refreshTokenService.refreshToken(refreshRequestModel);
    }
}
