package com.example.springsecurityjwt.services.refreshTokenService;

import com.example.springsecurityjwt.models.RefreshRequestModel;
import com.example.springsecurityjwt.models.ResponseModel;

public interface RefreshTokenService {
    ResponseModel refreshToken(RefreshRequestModel refreshRequestModel);
}
