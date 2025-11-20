package com.example.demo.user.repo;

import com.example.demo.user.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long>{
    Role findByRoleName(String roleName);
}
