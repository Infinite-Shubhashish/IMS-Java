package com.example.demo.utils;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ApiResponseBuilder {
    private final Map<String, Object> map = new HashMap<>();

    public ApiResponseBuilder status(HttpStatus status){
           map.put("code", status.value());
           map.put("status", status.getReasonPhrase());
           return this;
    }

    public ApiResponseBuilder message(String message) {
        map.put("message", message);
        return this;
    }

    public ApiResponseBuilder data(Object data) {
        if (data != null) {
            map.put("data", data);
        }
        return this;
    }

    public Map<String, Object> build() {
        map.put("timestamp", LocalDateTime.now());
        return map;
    }

}
