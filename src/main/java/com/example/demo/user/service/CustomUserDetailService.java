package com.example.demo.user.service;

import com.example.demo.user.model.User;
import com.example.demo.user.model.UserPrincipal;
import com.example.demo.user.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private static final Logger logger =
            LoggerFactory.getLogger(CustomUserDetailService.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User not Found");
        }

        UserPrincipal principal = new UserPrincipal(user);

        logger.info("➡️ Logged in user: " + user.getUsername());


        return principal;
    }
}
