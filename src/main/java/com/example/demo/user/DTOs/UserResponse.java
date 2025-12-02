package com.example.demo.user.DTOs;

import com.example.demo.user.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private List<Role> roles;
}
