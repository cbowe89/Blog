package com.ContentMgtSystem.Blog.repositories;

import com.ContentMgtSystem.Blog.entities.Role;
import com.ContentMgtSystem.Blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
