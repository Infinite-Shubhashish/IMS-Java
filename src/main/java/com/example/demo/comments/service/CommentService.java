package com.example.demo.comments.service;

import com.example.demo.comments.DTOs.CommentRequest;
import com.example.demo.comments.DTOs.CommentResponse;
import com.example.demo.comments.model.Comment;
import com.example.demo.comments.repo.CommentRepository;
import com.example.demo.posts.model.Post;
import com.example.demo.posts.model.PostStatus;
import com.example.demo.posts.repo.PostRepo;
import com.example.demo.user.model.User;
import com.example.demo.user.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepository  commentRepo;

    @Autowired
    private UserRepo userRepo;

    public CommentResponse saveComment(CommentRequest commentRequest, Long postId, String username) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if(post.getStatus() != PostStatus.APPROVED){
            throw new IllegalStateException("Comments can only be added to Approved Posts");
        }

        User user = userRepo.findByUsername(username);

        if(user == null){
            throw new EntityNotFoundException("User not found");
        }

        Comment comment = new Comment();

        comment.setContent(commentRequest.getContent());
        comment.setPost(post);
        comment.setCreator(user);
        commentRepo.save(comment);

        return mapToResponse(comment);

    }

    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        if (!comment.getCreator().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to delete this comment");
        }

        commentRepo.delete(comment);
    }

    public List<CommentResponse> getAllComments() {
        return commentRepo.findAll().stream().map(this::mapToResponse).toList();
    }

    public List<CommentResponse> getCommentsByPostId(Long postId){
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        return commentRepo.findByPost(post).stream().map(this::mapToResponse).toList();
    }


    private CommentResponse mapToResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setCreatedDate(comment.getCreatedDate());
        response.setUpdatedDate(comment.getUpdatedDate());
        response.setPostId(comment.getPost().getId());
        response.setCreatorUsername(comment.getCreator().getUsername());
        return response;
    }
}
