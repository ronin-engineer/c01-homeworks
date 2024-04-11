package com.roninhub.security.domain.auth.service;

import com.roninhub.security.application.constant.ResponseCode;
import com.roninhub.security.application.dto.exception.BusinessException;
import com.roninhub.security.domain.auth.dto.LoginRequest;
import com.roninhub.security.domain.auth.dto.LoginResponse;
import com.roninhub.security.domain.auth.repository.UserRepository;
import com.roninhub.security.infrastructure.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public LoginResponse authenticate(LoginRequest request) {
        //1. Get user by username in database
        var user = userRepository.findByUsername(request.getUsername());

        if (Objects.isNull(user)) {
            throw new BusinessException(ResponseCode.USER_NOT_FOUND);
        }

        // 2. verify password
        if (!user.getPassword().equals(request.getPassword())) {
            throw new BusinessException(ResponseCode.USERNAME_OR_PASSWORD_INVALID);
        }

        // 3. Generate jwt for user
        var jwt = JwtUtils.generateJwtToken(user);

        return LoginResponse.of(user, jwt);
    }
}
