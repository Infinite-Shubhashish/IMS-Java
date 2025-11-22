package com.example.demo.posts.controller;

import com.example.demo.posts.DTOs.PostRequest;
import com.example.demo.posts.DTOs.PostResponse;
import com.example.demo.posts.DTOs.RejectRequest;
import com.example.demo.posts.service.PostService;
import com.example.demo.user.model.UserPrincipal;
import com.example.demo.utils.ApiResponseBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping(value = "/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    //create post
    @PostMapping
    public ResponseEntity<Map<String,Object>> post(@Valid @RequestBody PostRequest postRequest, @AuthenticationPrincipal UserPrincipal user){

        PostResponse postResponse = postService.createPost(postRequest, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ApiResponseBuilder()
                .status(HttpStatus.CREATED)
                .message("Post created successfully")
                .data(postResponse)
                .build()
        );
    }

    //update post
    @PutMapping("/{postId}")
    public ResponseEntity<Map<String,Object>> updatePost(@PathVariable Long postId, @Valid @RequestBody PostRequest postRequest, @AuthenticationPrincipal UserPrincipal user){

        PostResponse postResponse = postService.updatePost(postId, postRequest, user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Post updated successfully")
                .data(postResponse)
                .build()
        );
    }

    //submit post
    @PostMapping("/{postId}/submit")
    public ResponseEntity<Map<String,Object>> submitPost(@PathVariable Long postId, @AuthenticationPrincipal UserPrincipal user){

        PostResponse postResponse = postService.submitForApproval(postId, user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Post submitted successfully")
                .data(postResponse)
                .build()
        );
    }

    //getpost by id
    @GetMapping("/{id}")
    public ResponseEntity<Map<String,Object>> getPost(@PathVariable Long id){
        PostResponse postResponse = postService.getPost(id);

         return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Post submitted successfully")
                .data(postResponse)
                .build()
        );

    }

    //get all posts
    @GetMapping
    public ResponseEntity<Map<String,Object>> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "createdDate") String sortBy){

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        Page<PostResponse> postResponses = postService.getPosts(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Posts fetched successfully")
                .data(postResponses)
                .build()
        );
    }

    //For admin
    @PostMapping("/{id}/close")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> closePost(@PathVariable Long id, @RequestBody(required = false) RejectRequest request){
        
        String comment = (request != null) ? request.getAdminComment() : null;

        PostResponse postResponse = postService.adminClose(id, comment);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Post closed successfully")
                .data(postResponse)
                .build()
        );
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> approvePost(@PathVariable Long id){
        PostResponse postResponse = postService.adminApprove(id);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Post approved successfully")
                .data(postResponse)
                .build()
        );
    }

    @PostMapping("/{id}/reject")
     @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Object>> rejectPost(@PathVariable Long id, @RequestBody(required = false) RejectRequest request){

        String comment = (request != null) ? request.getAdminComment() : null;

        PostResponse postResponse = postService.adminReject(id, comment);
        
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Post rejected successfully")
                .data(postResponse)
                .build()
        );
    }
}
