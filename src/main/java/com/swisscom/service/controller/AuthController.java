package com.swisscom.service.controller;

import com.swisscom.service.domain.dto.LoginRequest;
import com.swisscom.service.domain.dto.LoginResponse;
import com.swisscom.service.service.UserService;
import com.swisscom.service.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);
        return new ApiResponse.ApiResponseBuilder<>().withHttpStatus(HttpStatus.OK)
                .withData(loginResponse)
                .withMessage("User login successful.")
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody LoginRequest loginRequest) {
        userService.register(loginRequest);
        return new ApiResponse.ApiResponseBuilder<>().withHttpStatus(HttpStatus.CREATED)
                .withMessage("User created successfully.")
                .build();
    }
}
