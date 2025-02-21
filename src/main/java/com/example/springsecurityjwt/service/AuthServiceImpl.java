package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.entity.OurUsers;
import com.example.springsecurityjwt.models.RequestModel;
import com.example.springsecurityjwt.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@Service

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JWTUtilsImpl jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, JWTUtilsImpl jwtUtils,
                           PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public RequestModel registration(RequestModel requestModel) {
        RequestModel requestM = new RequestModel();
        try {
            OurUsers user = new OurUsers();
            user.setEmail(requestModel.getEmail());
            user.setPassword(passwordEncoder.encode(requestModel.getPassword()));
            user.setRole(requestModel.getRole());
            OurUsers savedUser = userRepository.save(user);
            if (savedUser != null && savedUser.getId() > 0) {
                requestM.setOurUsers(savedUser);
                requestM.setMessage("Пользователь успешно зарегистрирован!");
                requestM.setStatusCode(200);
            }
        } catch (Exception e) {
            requestM.setStatusCode(500);
            requestM.setError(e.getMessage());
        }
        return requestM;
    }

    @Override
    public RequestModel singIn(RequestModel requestModel) {
        RequestModel requestM = new RequestModel();
        var user = userRepository.findByEmail(requestModel.getEmail()).orElseThrow();
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(requestModel
                            .getEmail(), requestModel
                            .getPassword()));
            var jwt = jwtUtils.generateToken(user);
            var refreshJwt = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            user.setCounter(0);
            userRepository.save(user);
            requestM.setStatusCode(200);
            requestM.setToken(jwt);
            requestM.setRefreshToken(refreshJwt);
            requestM.setMessage("Успешный вход");
            requestM.setExpirationTime("1hr");
        } catch (Exception e) {
            analysis(user,requestM);
        }
        return requestM;
    }

    private void analysis (OurUsers user,RequestModel requestM){
        user.incrementCounter();
        if (user.getCounter() >= 5) {
            user.setNonLock(false);
            userRepository.save(user);
            requestM.setMessage("Вы 5 раз подряд ввели неверный пароль. Аккаунт заблокирован!" +
                    " Для разблокировки аккаунта обратитесь к администратору.");
            requestM.setStatusCode(500);
        } else {
            userRepository.save(user);
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT,
                    "Неправильный пароль! Осталось попыток " + (5 - user.getCounter()));
        }

    }

    public RequestModel refreshToken(RequestModel tokenForRefreshed) {
        RequestModel requestM = new RequestModel();
        OurUsers user =
                userRepository.findByEmail(jwtUtils.extractUserName(tokenForRefreshed.getToken())).orElseThrow();
        if (jwtUtils.isTokenValid(tokenForRefreshed.getToken(), user)) {
            var jwt = jwtUtils.generateToken(user);
            requestM.setStatusCode(200);
            requestM.setToken(jwt);
            requestM.setRefreshToken(tokenForRefreshed.getToken());
            requestM.setMessage("Успешно заменили токен!");
            requestM.setExpirationTime("1hr");
        }
        requestM.setStatusCode(500);
        return requestM;
    }
}
