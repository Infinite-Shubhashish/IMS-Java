package com.example.demo.user.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import lombok.ToString;


import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Length(min = 4, max = 40)
    private String username;

    @Length(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain uppercase, lowercase, number, and special character"
    )
    private String password;

    @NotNull
    @NotEmpty
    private String firstName;
    private String lastName;

    @Email
    private String email;

    @JsonIgnore
    private boolean isAccountExpired;
    @JsonIgnore
    private boolean isLocked;
    @JsonIgnore
    private boolean isEnabled;
    @JsonIgnore
    private boolean isCredentialsExpired;

    @Pattern(regexp="^(?:\\+977-?)?(9[0-9]{9})$", message="Invalid phone number")
    private String contact;

    @JsonIgnore
    private LocalDateTime createdDate;
    @JsonIgnore
    private LocalDateTime updatedDate;
    @JsonIgnore
    private LocalDateTime lastLoginDate;

    public User(){
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
        this.lastLoginDate = LocalDateTime.now();
        this.isAccountExpired = false;
        this.isLocked = false;
        this.isEnabled = false;
        this.isCredentialsExpired = false;
    }

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;
}
