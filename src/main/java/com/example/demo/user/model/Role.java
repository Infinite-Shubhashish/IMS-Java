package com.example.demo.user.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity(name = "ROLES")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String roleName;

//    @JsonIgnore
//    private LocalDateTime createdDate;
//    @JsonIgnore
//    private LocalDateTime updatedDate;
//
//    public Role() {
//        this.createdDate = LocalDateTime.now();
//        this.updatedDate = LocalDateTime.now();
//    }

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;



}
