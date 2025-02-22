package com.example.springsecurityjwt.services.adminService;

import com.example.springsecurityjwt.entity.OurUsers;
import com.example.springsecurityjwt.models.RegistrationAndSingInRequestModel;


public interface AdminService {
    OurUsers unlock(RegistrationAndSingInRequestModel registrationAndSingInRequestModel);
    OurUsers lock(RegistrationAndSingInRequestModel registrationAndSingInRequestModel);
}
