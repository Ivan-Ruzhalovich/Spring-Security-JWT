package com.example.springsecurityjwt.services.registrationService;

import com.example.springsecurityjwt.entity.OurUsers;
import com.example.springsecurityjwt.models.RegistrationAndSingInRequestModel;
import com.example.springsecurityjwt.models.ResponseModel;
import com.example.springsecurityjwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public ResponseModel registration(RegistrationAndSingInRequestModel registerRequestModel) {
        ResponseModel responseModel = new ResponseModel();
        try {
            OurUsers user = new OurUsers();
            user.setEmail(registerRequestModel.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequestModel.getPassword()));
            user.setRole(registerRequestModel.getRole());
            OurUsers savedUser = userRepository.save(user);
            if (savedUser != null && savedUser.getId() > 0) {
                responseModel.setOurUsers(savedUser);
                responseModel.setMessage("Пользователь успешно зарегистрирован!");
                responseModel.setStatusCode(200);
            }
        } catch (Exception e) {
            responseModel.setStatusCode(500);
            responseModel.setError(e.getMessage());
        }
        return responseModel;
    }
}
