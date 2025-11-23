package com.example.demo.posts.controller;

import com.example.demo.posts.DTOs.PostRequest;
import com.example.demo.posts.DTOs.PostResponse;
import com.example.demo.posts.DTOs.RejectRequest;
import com.example.demo.posts.model.PostStatus;
import com.example.demo.posts.model.PostType;
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
    public ResponseEntity<Map<String, Object>> post(@Valid @RequestBody PostRequest postRequest, @AuthenticationPrincipal UserPrincipal user) {

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
    public ResponseEntity<Map<String, Object>> updatePost(@PathVariable Long postId, @Valid @RequestBody PostRequest postRequest, @AuthenticationPrincipal UserPrincipal user) {

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
    public ResponseEntity<Map<String, Object>> submitPost(@PathVariable Long postId, @AuthenticationPrincipal UserPrincipal user) {

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
    public ResponseEntity<Map<String, Object>> getPost(@PathVariable Long id) {
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
    public ResponseEntity<Map<String, Object>> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "createdDate") String sortBy) {

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
    public ResponseEntity<Map<String, Object>> closePost(@PathVariable Long id, @RequestBody(required = false) RejectRequest request) {

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
    public ResponseEntity<Map<String, Object>> approvePost(@PathVariable Long id) {
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
    public ResponseEntity<Map<String, Object>> rejectPost(@PathVariable Long id, @RequestBody(required = false) RejectRequest request) {

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

    //Filter posts
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getMyPosts(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(defaultValue = "createdDate") String sortBy,
                                                          @AuthenticationPrincipal UserPrincipal user) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        Page<PostResponse> postResponses = postService.getPostsByUsername(user.getUsername(), pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("My posts fetched successfully")
                        .data(postResponses)
                        .build()
        );
    }


    //Filter posts by status
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getPostsByStatus(@RequestParam(name = "value") PostStatus status,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(defaultValue = "createdDate") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        Page<PostResponse> postResponses = postService.getPostsByStatus(status, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("Posts by status fetched successfully")
                        .data(postResponses)
                        .build()
        );
    }

    //Filter posts by type
    @GetMapping("/type")
    public ResponseEntity<Map<String, Object>> getPostsByType(@RequestParam(name = "value") PostType type,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(defaultValue = "createdDate") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        Page<PostResponse> postResponses = postService.getPostsByType(type, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("Posts by type fetched successfully")
                        .data(postResponses)
                        .build()
        );
    }

    //Filter posts by username and status
    @GetMapping("/me/status")
    public ResponseEntity<Map<String, Object>> getMyPostsByStatus(@RequestParam(name = "value") PostStatus status,
                                                                  @RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "10") int size,
                                                                  @RequestParam(defaultValue = "createdDate") String sortBy,
                                                                  @AuthenticationPrincipal UserPrincipal user) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        Page<PostResponse> postResponse = postService.getPostsByUsernameAndStatus(user.getUsername(), status, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("My posts filtered by status")
                        .data(postResponse)
                        .build()
        );
    }

    @GetMapping("/me/type")
    public ResponseEntity<Map<String, Object>> getMyPostsByType(@RequestParam(name = "value") PostType type,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(defaultValue = "createdDate") String sortBy,
                                                                @AuthenticationPrincipal UserPrincipal user) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        Page<PostResponse> postResponse = postService.getPostsByUsernameAndType(user.getUsername(), type, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponseBuilder()
                        .status(HttpStatus.OK)
                        .message("My posts filtered by type")
                        .data(postResponse)
                        .build()
        );
    }

    //Counts posts by status
    @GetMapping("/stats/status")
    public ResponseEntity<Map<PostStatus, Long>> getPostCountsByStatus() {
        return ResponseEntity.status(HttpStatus.OK).body(
                postService.getPostCountsByStatus()
        );
    }

    //Counts posts by type
    @GetMapping("/stats/type")
    public ResponseEntity<Map<PostType, Long>> getPostCountsByType() {
        return ResponseEntity.status(HttpStatus.OK).body(
                postService.getPostCountsByType()
        );
    }

    //Counts my posts by status
    @GetMapping("/me/stats/status")
    public ResponseEntity<Map<PostStatus, Long>> getMyPostCountsByStatus(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.status(HttpStatus.OK).body(
                postService.getMyPostByStatus(user.getUsername())
        );
    }

    //Counts my posts by type
    @GetMapping("/me/stats/type")
    public ResponseEntity<Map<PostType, Long>> getMyPostCountsByType(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.status(HttpStatus.OK).body(
                postService.getMyPostByType(user.getUsername())
        );
    }

    //Count total post
    @GetMapping("/total")
    public ResponseEntity<Map<String ,Long>> getTotalPosts(){
        return ResponseEntity.status(HttpStatus.OK).body(
                Map.of("totalPosts", postService.getTotalPosts())
        );
    }

    @GetMapping("/me/total")
    public ResponseEntity<Map<String, Long>> getMyTotalPosts(@AuthenticationPrincipal UserPrincipal user){
        return ResponseEntity.status(HttpStatus.OK).body(
                Map.of("totalPosts",postService.getMyTotalPosts(user.getUsername()))
        );
    }
}

