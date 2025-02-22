package com.example.springsecurityjwt.services.singInService;

import com.example.springsecurityjwt.entity.OurUsers;
import com.example.springsecurityjwt.models.RegistrationAndSingInRequestModel;
import com.example.springsecurityjwt.models.ResponseModel;
import com.example.springsecurityjwt.repository.UserRepository;
import com.example.springsecurityjwt.services.jwtService.JWTUtilsImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
@Service
@AllArgsConstructor
public class SingInServiceImpl implements SingInService{

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtilsImpl jwtUtils;

    public ResponseModel authenticate(RegistrationAndSingInRequestModel authenticateModel) {
        ResponseModel responseModel = new ResponseModel();
        OurUsers user = userRepository
                .findByEmail(authenticateModel.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь не найден"));
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authenticateModel
                            .getEmail(), authenticateModel
                            .getPassword()));

        } catch (AuthenticationException e) {
            if (e.getMessage().equals("Bad credentials"))
                analysis(user, responseModel);
            if (e.getMessage().equals("User account is locked"))
                throw new ResponseStatusException(HttpStatus.LOCKED,"Аккаунт заблокирован. Для разблокировки обратитесь к администратору!");
            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Что то сломалось!");
        }
        String jwt = jwtUtils.generateToken(user);
        String refreshJwt = jwtUtils.generateRefreshToken(new HashMap<>(), user);
        user.setCounter(0);
        userRepository.save(user);
        responseModel.setStatusCode(200);
        responseModel.setToken(jwt);
        responseModel.setRefreshToken(refreshJwt);
        responseModel.setMessage("Успешный вход");
        responseModel.setExpirationTime("1hr");
        return responseModel;
    }

    private void analysis (OurUsers user,ResponseModel responseModel){
        user.incrementCounter();
        if (user.getCounter() >= 5) {
            user.setNonLock(false);
            userRepository.save(user);
            responseModel.setMessage("Вы 5 раз подряд ввели неверный пароль. Аккаунт заблокирован!" +
                    " Для разблокировки аккаунта обратитесь к администратору.");
            responseModel.setStatusCode(500);
        } else {
            userRepository.save(user);
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT,
                    "Неправильный пароль! Осталось попыток " + (5 - user.getCounter()));
        }

    }
}
