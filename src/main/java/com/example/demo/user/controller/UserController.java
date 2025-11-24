package com.example.demo.user.controller;

import com.example.demo.user.DTOs.UserResponse;
import com.example.demo.user.model.User;
import com.example.demo.user.service.UserService;
import com.example.demo.utils.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "/users")
    public ResponseEntity<Map<String, Object>> getAllUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "username") String sortBy
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<UserResponse> usersResponses = userService.getAllUsers(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponseBuilder()
                    .status(HttpStatus.OK)
                    .message("Users fetched successfully")
                    .data(usersResponses)
                    .build()
        );
    }

    //count users
    @GetMapping(value = "users/total")
    public ResponseEntity<Map<String, Long>> countUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(
            Map.of("totalUsers", userService.getTotalUsers())
        );
    }
}
