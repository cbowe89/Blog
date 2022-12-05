package com.ContentMgtSystem.Blog.repositories;

import com.ContentMgtSystem.Blog.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query(value = "SELECT tag FROM tag WHERE tag = ?", nativeQuery = true)
    String findStringByContent(String tagContent);

    @Query(value = "SELECT * FROM tag WHERE tag = ?", nativeQuery = true)
    Tag findTagByContent(String tagContent);
}
