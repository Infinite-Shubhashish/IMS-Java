package com.example.demo.posts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity(name = "POSTS")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String title;

    private String description;

    @JsonIgnore
    private LocalDateTime createdDate;
    @JsonIgnore
    private LocalDateTime updatedDate;



}
