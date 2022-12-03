package com.ContentMgtSystem.Blog.repositories;

import com.ContentMgtSystem.Blog.entities.Post;
import com.ContentMgtSystem.Blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUser(User user);

    // Get the most recent post based on Timestamp that has approved status
  
    @Query(value = "SELECT * FROM post WHERE status_name = 'approved' ORDER BY created_date DESC LIMIT 1", nativeQuery = true)

    List<Post> findAllByTimestamp();

    // Get all posts in descending order based on Timestamp
    @Query(value = "SELECT * FROM post WHERE status_id = 2 ORDER BY created_date DESC",
            nativeQuery = true)
    List<Post> findAllByOrderByCreated_dateDesc();

    // display the pending posts
    @Query(value = "SELECT p.* from post AS p JOIN post_status AS ps" +
            " ON p.status_id = ps.status_id" +
            " WHERE ps.status_name = 'pending';", nativeQuery = true)
    List<Post> findAllByPending();




}
