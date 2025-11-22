package com.example.demo.user.controller;

import com.example.demo.user.model.Role;
import com.example.demo.user.service.RoleService;
import com.example.demo.utils.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/roles")
    public ResponseEntity<Map<String, Object>> saveRole(@RequestBody Role role){
        roleService.saveRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.CREATED)
                        .message("Role added successfully!")
                        .build()
        );
    }

    @GetMapping(value="/roles")
    public ResponseEntity<Map<String, Object>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("Roles fetched successfully")
                        .data(roles)
                        .build()

        );
    }

}
