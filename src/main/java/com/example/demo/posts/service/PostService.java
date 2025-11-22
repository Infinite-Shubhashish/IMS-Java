package com.example.demo.posts.service;

import com.example.demo.posts.DTOs.PostRequest;
import com.example.demo.posts.DTOs.PostResponse;
import com.example.demo.posts.model.Post;
import com.example.demo.posts.model.PostType;
import com.example.demo.posts.repo.PostRepo;
import com.example.demo.user.model.User;
import com.example.demo.user.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import com.example.demo.posts.model.PostStatus;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    //Create post
    public PostResponse createPost(PostRequest postRequest, String username) {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        Post post = modelMapper.map(postRequest, Post.class);
        post.setCreator(user);

        Post saved = postRepo.save(post);

        return mapToResponse(saved);

    }

    //Update post
    public PostResponse updatePost(Long postId, PostRequest postRequest, String username) {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (!post.getCreator().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to update this post");
        }

        if (post.getStatus() != PostStatus.DRAFT && post.getStatus() != PostStatus.REJECTED) {
            throw new IllegalStateException("Only draft or rejected posts can be updated");
        }

        modelMapper.map(postRequest, post);
        return mapToResponse(post);
    }

    //post for approval
    public PostResponse submitForApproval(Long postId, String username) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (!post.getCreator().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to update this post");
        }

        if (post.getStatus() != PostStatus.DRAFT && post.getStatus() != PostStatus.REJECTED) {
            throw new IllegalStateException("Only draft or rejected posts can be set for approval");
        }

        post.setStatus(PostStatus.PENDING_APPROVAL);

        return mapToResponse(post);
    }

    //post by Id
    public PostResponse getPost(Long id) {
        Post post = postRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        return mapToResponse(post);
    }

    //pagination
    public Page<PostResponse> getPosts(Pageable pageable) {
        return postRepo.findAll(pageable)
                .map(this::mapToResponse);
    }

    // for admin

    //approve post
    public PostResponse adminApprove(Long postId) {
        Post post = getPendingPost(postId);
        post.setStatus(PostStatus.APPROVED);

        return mapToResponse(post);
    }

    //reject post
    public PostResponse adminReject(Long postId, String comment) {
        Post post = getPendingPost(postId);
        post.setStatus(PostStatus.REJECTED);

        if (comment != null) {
            post.setAdminComment(comment);
        }

        return mapToResponse(post);
    }

    //close post
    public PostResponse adminClose(Long postId, String comment) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (comment != null) {
            post.setAdminComment(comment);
        }
        post.setStatus(PostStatus.CLOSED);
        return mapToResponse(post);
    }

    //find pending post
    private Post getPendingPost(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (post.getStatus() != PostStatus.PENDING_APPROVAL) {
            throw new IllegalStateException("Only PENDING_APPROVAL posts can be approved/rejected");
        }

        return post;
    }


    private PostResponse mapToResponse(Post post) {
        PostResponse postResponse = modelMapper.map(post, PostResponse.class);
        postResponse.setCreatedById(post.getCreator().getId());
        postResponse.setCreatedByUsername(post.getCreator().getUsername());

        return postResponse;
    }

    //filter posts
    public Page<PostResponse> getPostsByUsername(String username, Pageable pageable) {
        return postRepo.findByCreator_Username(username, pageable)
                .map(this::mapToResponse);
    }

    //filter posts by status
    public Page<PostResponse> getPostsByStatus(PostStatus status, Pageable pageable) {
        return postRepo.findByStatus(status, pageable)
                .map(this::mapToResponse);
    }

    //filter posts by type
    public Page<PostResponse> getPostsByType(PostType type, Pageable pageable) {
        return postRepo.findByType(type, pageable)
                .map(this::mapToResponse);
    }

    //filter posts by username and status
    public Page<PostResponse> getPostsByUsernameAndStatus(String username, PostStatus status,
                                                          Pageable pageable) {
        return postRepo.findByCreator_UsernameAndStatus(username, status, pageable)
                .map(this::mapToResponse);
    }

    //filter posts by username and type
    public Page<PostResponse> getPostsByUsernameAndType(String username, PostType type,
                                                        Pageable pageable) {
        return postRepo.findByCreator_UsernameAndType(username, type, pageable)
                .map(this::mapToResponse);


    }
}