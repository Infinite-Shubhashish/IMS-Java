package com.example.demo.posts.DTOs;

import com.example.demo.posts.model.PostStatus;
import com.example.demo.posts.model.PostType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private Long id;
    private String title;
    private String description;
    private PostType postType;
    private PostStatus postStatus;
    private String adminComment;
    private Long createdById;
    private String createdByUsername;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
