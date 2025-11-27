package com.example.demo.comments.controller;

import com.example.demo.comments.DTOs.CommentRequest;
import com.example.demo.comments.DTOs.CommentResponse;
import com.example.demo.comments.model.Comment;
import com.example.demo.comments.service.CommentService;
import com.example.demo.user.model.UserPrincipal;
import com.example.demo.utils.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<Map<String, Object>> getCommentsForPost(
            @PathVariable Long postId
    ) {
        List<CommentResponse> comments = commentService.getCommentsByPostId(postId);

        return ResponseEntity.ok(
                new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("Comments fetched successfully")
                        .data(comments)
                        .build()
        );
    }


    @PostMapping(value = "{postId}")
    public ResponseEntity<Map<String, Object>> postComment(
            @RequestBody CommentRequest commentRequest,
            @PathVariable("postId") Long postId,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        CommentResponse commentResponse = commentService.saveComment(commentRequest, postId, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.CREATED)
                        .message("Comments added successfully")
                        .data(commentResponse)
                        .build()
        );
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Map<String, Object>> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserPrincipal user
    ) {
        commentService.deleteComment(commentId, user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("Comment deleted successfully")
                        .build()
        );
    }

}
