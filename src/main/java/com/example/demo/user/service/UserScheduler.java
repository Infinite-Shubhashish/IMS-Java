package com.example.demo.user.service;
import com.example.demo.user.model.User;

import com.example.demo.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class UserScheduler {

    @Autowired
    private UserRepo userRepo;

    @Scheduled(cron = "0 0 2 * * *")
    public void checkExpiredAccounts(){
        System.out.println("Running Account Expiry Check");
        LocalDateTime now = LocalDateTime.now();

         List<User> users = userRepo.findAll();

        for (User user : users) {
            if (user.getLastLoginDate().plusDays(180).isBefore(now)) {

                user.setAccountExpired(true);
                userRepo.save(user);

            }

            if (user.getUpdatedDate() != null &&
                user.getLastLoginDate().plusDays(90).isBefore(now)){
                user.setCredentialsExpired(true);
                userRepo.save(user);
            }
        }

    }
    
}
