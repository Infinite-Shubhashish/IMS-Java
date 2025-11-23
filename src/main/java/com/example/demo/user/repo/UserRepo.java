package com.example.demo.user.repo;

import com.example.demo.user.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET last_login_date = :date WHERE username = :username", nativeQuery = true)
    void updateLastLogin(@Param("username") String username,
                         @Param("date") LocalDateTime date);

}
