package com.example.demo.posts.DTOs;

import com.example.demo.posts.model.PostType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private PostType type;


}
