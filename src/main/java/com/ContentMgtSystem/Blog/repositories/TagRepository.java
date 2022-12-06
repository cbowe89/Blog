package com.ContentMgtSystem.Blog.repositories;

import com.ContentMgtSystem.Blog.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query(value = "SELECT tag_name FROM tag WHERE tag_name = ?", nativeQuery = true)
    String findStringByContent(String tagContent);

    @Query(value = "SELECT * FROM tag WHERE tag_name = ?", nativeQuery = true)
    Tag findTagByContent(String tagContent);
}
