package com.example.springsecurityjwt.services.refreshTokenService;

import com.example.springsecurityjwt.entity.OurUsers;
import com.example.springsecurityjwt.models.RefreshRequestModel;
import com.example.springsecurityjwt.models.ResponseModel;
import com.example.springsecurityjwt.repository.UserRepository;
import com.example.springsecurityjwt.services.jwtService.JWTUtilsImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserRepository userRepository;
    private final JWTUtilsImpl jwtUtils;

    @Override
    public ResponseModel refreshToken(RefreshRequestModel refreshRequestModel) {
        ResponseModel responseModel = new ResponseModel();
        OurUsers user =
                userRepository.findByEmail(jwtUtils.extractUserName(refreshRequestModel.getToken())).orElseThrow();
        if (jwtUtils.isTokenValid(refreshRequestModel.getToken(), user)) {
            var jwt = jwtUtils.generateToken(user);
            responseModel.setStatusCode(200);
            responseModel.setToken(jwt);
            responseModel.setRefreshToken(refreshRequestModel.getToken());
            responseModel.setMessage("Успешно заменили токен!");
            responseModel.setExpirationTime("1hr");
        }
        responseModel.setStatusCode(500);
        return responseModel;
    }
}
