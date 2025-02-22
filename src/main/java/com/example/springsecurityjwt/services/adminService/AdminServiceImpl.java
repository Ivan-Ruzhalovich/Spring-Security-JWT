package com.example.springsecurityjwt.services.adminService;

import com.example.springsecurityjwt.entity.OurUsers;
import com.example.springsecurityjwt.models.RegistrationAndSingInRequestModel;
import com.example.springsecurityjwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final UserRepository repository;

    @Override
    public OurUsers unlock(RegistrationAndSingInRequestModel registrationAndSingInRequestModel) {
        OurUsers user = repository.findByEmail(registrationAndSingInRequestModel.getEmail()).orElseThrow();
        user.setCounter(0);
        user.setNonLock(true);
        return repository.save(user);
    }

    @Override
    public OurUsers lock(RegistrationAndSingInRequestModel registrationAndSingInRequestModel) {
        OurUsers user = repository.findByEmail(registrationAndSingInRequestModel.getEmail()).orElseThrow();
        user.setNonLock(false);
        return repository.save(user);
    }
}
