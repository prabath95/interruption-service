package com.swisscom.service.utils;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Map;

@JsonPropertyOrder({"statusCode", "message", "data", "additionalParams"})
@Getter
public class ApiResponse<T> {

    private int statusCode;
    private String message;
    private T data;
    private Map<String, Object> additionalParams;

    private ApiResponse(ApiResponseBuilder builder) {
        this.statusCode = builder.statusCode;
        this.message = builder.message;
        this.data = (T) builder.data;
        this.additionalParams = builder.additionalParams;
    }

    protected ApiResponse() {
    }

    public static class ApiResponseBuilder<T> {

        private int statusCode = HttpStatus.OK.value();
        private String message = "";
        private Map<String, Object> additionalParams = Collections.<String, Object>emptyMap();
        private T data;

        public ApiResponseBuilder<T> withData(T data) {
            this.data = data;
            return this;
        }

        public ApiResponseBuilder<T> withMessage(String message) {
            this.message = message;
            return this;
        }

        public ApiResponseBuilder<T> withHttpStatus(HttpStatus httpStatus) {
            this.message = httpStatus.getReasonPhrase();
            this.statusCode = httpStatus.value();
            return this;
        }

        public ApiResponseBuilder<T> withAdditionalParams(Map<String, Object> additionalParams) {
            this.additionalParams = additionalParams;
            return this;
        }

        public ResponseEntity<ApiResponse> build() {
            ApiResponse<T> apiResponse = new ApiResponse<>(this);
            if (apiResponse.statusCode == HttpStatus.OK.value()) {
                return ResponseEntity.ok(apiResponse);
            }
            return ResponseEntity.status(HttpStatus.valueOf(this.statusCode))
                    .body(apiResponse);
        }
    }
}
