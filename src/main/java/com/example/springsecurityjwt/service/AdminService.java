package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.entity.OurUsers;
import com.example.springsecurityjwt.models.RequestModel;

public interface AdminService {
    OurUsers unlock(RequestModel requestModel);
    OurUsers lock(RequestModel requestModel);
}
