package com.swisscom.service.domain.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class LoginResponse {
    private String token;
    private String username;
    private String userRole;
}
