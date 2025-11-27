package com.example.demo.posts.repo;

import com.example.demo.posts.model.Post;
import com.example.demo.posts.model.PostStatus;
import com.example.demo.posts.model.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    Page<Post> findByCreator_Username(String username, Pageable pageable);

    Page<Post> findByStatus(PostStatus status, Pageable pageable);

    Page<Post> findByType(PostType type, Pageable pageable);

    Page<Post> findByCreator_UsernameAndStatus(String username, PostStatus status, Pageable pageable);

    Page<Post> findByCreator_UsernameAndType(String username, PostType type, Pageable pageable);

    @Query("SELECT p.status as status, COUNT(p) as count FROM POSTS p GROUP BY p.status")
    List<Map<String, Object>> countPostsByStatus();

    @Query("SELECT p.type as type, COUNT(p) as count FROM POSTS p GROUP BY p.type")
    List<Map<String, Object>> countPostsByType();

    @Query("SELECT p.status as status, COUNT(p) as count FROM POSTS p WHERE p.creator.username = :username GROUP BY p.status")
    List<Map<String, Object>> countMyPostsByStatus(@Param("username") String username);

    @Query("SELECT p.type as type, COUNT(p) as count FROM POSTS p where p.creator.username = :username GROUP BY p.type ")
    List<Map<String, Object>> countMyPostsByType(@Param("username") String username);

    @Query("SELECT p FROM POSTS p WHERE p.status = 'APPROVED'")
    Page<Post> findVisiblePosts(String username, Pageable pageable);

    long countByCreator_Username(String username);
}
