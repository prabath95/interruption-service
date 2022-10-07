package com.swisscom.service.controller;

import com.swisscom.service.domain.dto.CreateUserRequest;
import com.swisscom.service.domain.dto.LoginRequest;
import com.swisscom.service.domain.dto.LoginResponse;
import com.swisscom.service.service.UserService;
import com.swisscom.service.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        LOGGER.info("Login user request {} ",loginRequest.getUsername());
        LoginResponse loginResponse = userService.login(loginRequest);
        LOGGER.info("Login user request {} response {} ",loginRequest.getUsername(),loginResponse);
        return new ApiResponse.ApiResponseBuilder<>().withHttpStatus(HttpStatus.OK)
                .withData(loginResponse)
                .withMessage("User login successful.")
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody CreateUserRequest createUserRequest) {
        LOGGER.info("Create user {} role {} ",createUserRequest.getUsername(),createUserRequest.getRole());
        userService.register(createUserRequest);
        LOGGER.info("Create user {} role {} success.",createUserRequest.getUsername(),createUserRequest.getRole());
        return new ApiResponse.ApiResponseBuilder<>().withHttpStatus(HttpStatus.CREATED)
                .withMessage("User created successfully.")
                .build();
    }
}
