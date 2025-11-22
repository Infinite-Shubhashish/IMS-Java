package com.example.demo.posts.repo;

import com.example.demo.posts.model.Post;
import com.example.demo.posts.model.PostStatus;
import com.example.demo.posts.model.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    Page<Post> findByCreator_Username(String username, Pageable pageable);

    Page<Post> findByStatus(PostStatus status, Pageable pageable);

    Page<Post> findByType(PostType type, Pageable pageable);

    Page<Post> findByCreator_UsernameAndStatus(String username, PostStatus status, Pageable pageable);

    Page<Post> findByCreator_UsernameAndType(String username, PostType type, Pageable pageable);
}
