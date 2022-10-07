package com.swisscom.service.service.impl;

import com.swisscom.service.config.JwtTokenUtil;
import com.swisscom.service.domain.dto.ApplicationUser;
import com.swisscom.service.domain.dto.LoginRequest;
import com.swisscom.service.domain.dto.LoginResponse;
import com.swisscom.service.domain.entity.User;
import com.swisscom.service.repository.UserRepository;
import com.swisscom.service.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InterruptionScheduleServiceImpl.class);
    private UserRepository userRepository;
    private JwtTokenUtil jwtUtil;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           JwtTokenUtil jwtUtil,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void register(LoginRequest loginRequest) {
        User user = User.builder()
                .userRole("ADMIN")
                .password(passwordEncoder.encode(loginRequest.getPassword()))
                .username(loginRequest.getUsername())
                .build();
        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        user.orElseThrow(() -> {
            LOGGER.info("User name not found for {} ", loginRequest.getUsername());
            return new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Username or Password.");
        });
        ApplicationUser applicationUser = modelMapper.map(user, ApplicationUser.class);

        if (!passwordEncoder.matches(loginRequest.getPassword(), applicationUser.getPassword())) {
            LOGGER.info("Invalid password for user {} ", loginRequest.getUsername());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Username or Password.");
        }

        String accessToken = jwtUtil.generateAccessToken(applicationUser);

        return LoginResponse.builder()
                .username(applicationUser.getUsername())
                .token(accessToken)
                .userRole(applicationUser.getUserRole())
                .build();
    }
}
