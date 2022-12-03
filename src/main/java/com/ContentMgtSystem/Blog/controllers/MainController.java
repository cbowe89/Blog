package com.ContentMgtSystem.Blog.controllers;

import com.ContentMgtSystem.Blog.entities.Post;
import com.ContentMgtSystem.Blog.entities.User;
import com.ContentMgtSystem.Blog.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    Post_StatusRepository post_statusRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("recentPosts",
                postRepository.findAllPostsDescWithLimit());
        return "index";
    }

    @GetMapping("/content")
    public String viewAllPosts(Model model) {
        model.addAttribute("posts",
                postRepository.findAllOrderByCreated_dateDesc());
        return "content";
    }

    @GetMapping("/writePost")
    public String writePost() {
        return "writePost";
    }

    @GetMapping("/admin")
    public String adminPage(Model model){
        // Admin page should display all posts, regardless of status, simply findAll()
        // Admin should be able to view and edit all posts, in addition to changing status
        model.addAttribute("pendings",postRepository.findAllByPending());
        return "admin";
    }

    @GetMapping("deletePost")
    public String deletePost(int post_id){
        postRepository.deleteById(post_id);
        return "redirect:/admin";
    }

    @GetMapping("adminReview")
    public String adminReviewPage(Model model){
        model.addAttribute("pendings",postRepository.findAllByPending());
        return "adminReview";
    }

    @GetMapping("approvePost") // GetMapping correct here
    public String approvePost(int post_id){
        Post post = postRepository.findById(post_id).get();

        // This line updates the wrong table (changed both status names to approved)
        // commented out so you can view difference in what happens to post
        // and post_status table when running that way
        // Here we want to update the post itself so the FK status_id on the post changes
        //post.getPost_status().setStatus_name("approved");
        // confirmed below updates correct table, setPost_status method from Post entity take post_status param
        post.setPost_status(post_statusRepository.findById(2).orElse(null));

        postRepository.save(post);

        return "redirect:/adminReview";
    }

    @GetMapping("user")
    public String userPage(int user_id, Model model) {
        User user = userRepository.findById(user_id).get();
        List<Post> userPosts = postRepository.findByUser(user);
        model.addAttribute("userPosts", userPosts);
        return "user";
    }

}
