package com.swisscom.service.utils;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class ErrorResponse<T> {
    private HttpStatus statusCode;
    private String statusDescription;
    private String errorDescription;
    private T errorData;
}
