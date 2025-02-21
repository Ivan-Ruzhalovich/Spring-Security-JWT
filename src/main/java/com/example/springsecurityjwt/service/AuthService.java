package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.models.RequestModel;

public interface AuthService {
    RequestModel registration(RequestModel requestModel);
    RequestModel singIn(RequestModel requestModel);
}
