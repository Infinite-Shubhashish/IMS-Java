package com.example.demo.user.DTOs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserStatusSummary {
    private long totalUsers;
    private long activeUsers;
    private long lockedUsers;
    private long expiredAccounts;
    private long credentialsExpired;
}
