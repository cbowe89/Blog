package com.ContentMgtSystem.Blog.repositories;

import com.ContentMgtSystem.Blog.entities.Post;
import com.ContentMgtSystem.Blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);

    // Get the most recent post based on Timestamp that has approved status
    // *** ONLY TO BE USED FOR HOME PAGE TO DISPLAY MOST RECENT POST ***
    @Query(value = "SELECT * FROM post WHERE status_id = 2 " +
            "AND (expiration_date > NOW() OR expiration_date IS NULL)" +
            "ORDER BY created_date DESC LIMIT 1",
            nativeQuery = true)
    List<Post> findAllPostsDescWithLimit();

    // Get all posts in descending order based on Timestamp
    // *** USED BY CONTENT PAGE TO DISPLAY ALL POSTS ***
    @Query(value = "SELECT * FROM post WHERE status_id = 2 " +
            "AND (expiration_date > NOW() OR expiration_date IS NULL)",
            nativeQuery = true)
    List<Post> findAllNotExpired();

    // display the pending posts
    @Query(value = "SELECT * FROM post WHERE status_id = 1", nativeQuery = true)
    List<Post> findAllByPending();
}
