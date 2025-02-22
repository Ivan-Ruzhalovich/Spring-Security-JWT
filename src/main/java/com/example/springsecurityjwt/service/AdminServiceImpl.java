package com.example.springsecurityjwt.services;

import com.example.springsecurityjwt.entity.OurUsers;
import com.example.springsecurityjwt.models.RequestModel;
import com.example.springsecurityjwt.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{
    private final UserRepository repository;

    public AdminServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public OurUsers unlock(RequestModel requestModel) {
        OurUsers user = repository.findByEmail(requestModel.getEmail()).orElseThrow();
        user.setCounter(0);
        user.setNonLock(true);
        return repository.save(user);
    }

    @Override
    public OurUsers lock(RequestModel requestModel) {
        OurUsers user = repository.findByEmail(requestModel.getEmail()).orElseThrow();
        user.setNonLock(false);
        return repository.save(user);
    }
}
