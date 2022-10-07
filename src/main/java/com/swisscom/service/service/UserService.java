package com.swisscom.service.service;

import com.swisscom.service.domain.dto.CreateUserRequest;
import com.swisscom.service.domain.dto.LoginRequest;
import com.swisscom.service.domain.dto.LoginResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserService {

    void register(CreateUserRequest createUserRequest);

    LoginResponse login(@RequestBody LoginRequest loginRequest);
}
