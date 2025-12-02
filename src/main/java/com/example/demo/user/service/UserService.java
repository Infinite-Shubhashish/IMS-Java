package com.example.demo.user.service;

import com.example.demo.jwt.service.JwtService;
import com.example.demo.user.DTOs.UserRequest;
import com.example.demo.user.DTOs.UserResponse;
import com.example.demo.user.DTOs.UserStatusSummary;
import com.example.demo.user.model.Role;
import com.example.demo.user.model.User;
import com.example.demo.user.repo.RoleRepo;
import com.example.demo.user.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private JwtService jwtService;

    public void registerUser(@RequestBody User user) {
        List<Role> roles = new ArrayList<>();

        for(Role role : user.getRoles()){
            Role existingRole = roleRepo.findByRoleName(role.getRoleName());
            if(existingRole == null){
                throw new RuntimeException("Role not available: " + role.getRoleName());
            }
            roles.add(existingRole);
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userRepo.save(user);
    }

    //Pagination for users
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<User> users = userRepo.findAll(pageable);

        return users.map(this::mapToResponse);
    }

    public Map<String, Object> validateUser(UserRequest userRequest) {
        Map<String, Object> map = new HashMap<>();

        User user = userRepo.findByUsername(userRequest.getUsername());

        if(user == null){
            throw new EntityNotFoundException("Invalid Username");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getUsername(),
                        userRequest.getPassword()
                )
        );

        updateLastLoginDate(user.getUsername());

        String token = jwtService.generateToken(user.getUsername());
        Date date = jwtService.extractExpiration(token);
        map.put("token", token);
        map.put("expiration", date);

        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getRoleName)
                .toList();

        map.put("roles", roles);

        return map;
    }

    public User getUserByUsername(@Length(min = 4, max = 40) String username) {
        return userRepo.findByUsername(username);
    }

    public void updateLastLoginDate(String username){
        userRepo.updateLastLogin(username, LocalDateTime.now());
    }

    //get count
    public long getTotalUsers(){
        return userRepo.count();
    }

    //mapToResponse method to convert User to UserResponse
    private UserResponse mapToResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());
        userResponse.setContact(user.getContact());
        userResponse.setRoles(user.getRoles());

        return userResponse;
    }


    //get User summary

    public UserStatusSummary getUserStatusSummary(){
        long total = userRepo.count();
        long active = userRepo.countByIsEnabled(true);
        long locked = userRepo.countByIsLocked(true);
        long expired = userRepo.countByIsAccountExpired(true);
        long credentialsExpired = userRepo.countByIsCredentialsExpired(true);

        return new UserStatusSummary(total, active, locked, expired, credentialsExpired);
    }

    public Map<String, Object> buildLoginResponse(User user) {

        updateLastLoginDate(user.getUsername());

        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getRoleName)
                .toList();

        Map<String, Object> data = new HashMap<>();
        data.put("username", user.getUsername());
        data.put("roles", roles);

        return data;
    }
}
