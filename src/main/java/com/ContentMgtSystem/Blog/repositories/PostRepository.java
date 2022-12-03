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
    @Query(value = "SELECT * FROM post WHERE status_id = 2 ORDER BY created_date DESC LIMIT 1", nativeQuery = true)
    List<Post> findAllByTimestamp();

<<<<<<< HEAD
=======
    // display the pending posts
    @Query(value = "SELECT * FROM post WHERE status_id=1", nativeQuery = true)
    List<Post> findAllByPending();

    // approve posts
    @Query(value = "UPDATE post SET status_id = 2 WHERE post_id=?",nativeQuery = true)
    Post changePostStatus(int post_id);
>>>>>>> master

}
