package com.example.demo.comments.DTOs;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long Id;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Long postId;
    private String creatorUsername;
}
