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
    @Query(value = "SELECT * FROM post WHERE status_id = 2 ORDER BY created_date DESC LIMIT 1",
            nativeQuery = true)
    List<Post> findAllPostsDescWithLimit();

    /* Confirmed this method give same results as simplified Query above (findAllPostsDescLimit1)
    @Query(value = "SELECT p.* from post AS p JOIN post_status AS ps" +
            " ON p.status_id = ps.status_id" +
            " WHERE ps.status_name = 'approved' ORDER BY created_date DESC LIMIT 1", nativeQuery = true)
    List<Post> findAllByTimestamp();
     */

    // Get all posts in descending order based on Timestamp
    // *** USED BY CONTENT PAGE TO DISPLAY ALL POSTS, NEWEST TO OLDEST ***
    @Query(value = "SELECT * FROM post WHERE status_id = 2 ORDER BY created_date DESC",
            nativeQuery = true)
    List<Post> findAllOrderByCreated_dateDesc();

    // display the pending posts
    // *** Could simplify this Query to "SELECT * FROM post WHERE status_id = 1 ORDER bY created_date DESC";
    @Query(value = "SELECT p.* from post AS p JOIN post_status AS ps" +
            " ON p.status_id = ps.status_id" +
            " WHERE ps.status_name = 'pending';", nativeQuery = true)
    List<Post> findAllByPending();

    // Adding this for example on get method works for approvePost
    @Query(value = "UPDATE post SET status_id = 2 WHERE post_id = ?", nativeQuery = true)
    Post updatePostStatus(Integer post_id);
}
