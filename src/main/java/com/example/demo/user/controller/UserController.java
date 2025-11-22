package com.example.demo.user.controller;

import com.example.demo.user.model.User;
import com.example.demo.user.service.UserService;
import com.example.demo.utils.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    public ResponseEntity<Map<String,Object>> getAllUsers(){
        List<User> users = userService.getAllUsers();

         return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("Users fetched successfully")
                        .data(users)
                        .build()

        );
    }
}
