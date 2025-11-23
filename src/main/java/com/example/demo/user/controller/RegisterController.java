package com.example.demo.user.controller;

import com.example.demo.user.model.User;
import com.example.demo.user.model.Role;
import com.example.demo.user.service.UserService;
import com.example.demo.utils.ApiResponseBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/auth")

public class RegisterController {

        @Autowired
        private UserService userService;

        @PostMapping(value = "/register")
        public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody User user) throws JsonProcessingException {
            userService.registerUser(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new ApiResponseBuilder()
                            .status(HttpStatus.CREATED)
                            .message("User registration successful")
                            .build()
            );
        }

        @PostMapping(value="/login")
        public ResponseEntity<Map<String, Object>> login(@RequestBody User user) throws JsonProcessingException {
            userService.validateUser(user);
            User existingUser = userService.getUserByUsername(user.getUsername());
            userService.updateLastLoginDate(existingUser.getUsername());

            List<String> roles = existingUser.getRoles()
            .stream()
            .map(Role::getRoleName)
            .toList();

            Map<String, Object> data = new HashMap<>();
            data.put("username", existingUser.getUsername());
            data.put("roles", roles);


            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponseBuilder()
                            .status(HttpStatus.OK)
                            .message("User logged in successfully")
                            .data(data)
                            .build()
            );
        }

    }

