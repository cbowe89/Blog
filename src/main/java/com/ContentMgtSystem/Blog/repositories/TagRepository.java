package com.ContentMgtSystem.Blog.repositories;

import com.ContentMgtSystem.Blog.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
}
