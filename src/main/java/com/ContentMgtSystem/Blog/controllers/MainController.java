package com.ContentMgtSystem.Blog.controllers;

import com.ContentMgtSystem.Blog.entities.Post;
import com.ContentMgtSystem.Blog.entities.Post_Status;
import com.ContentMgtSystem.Blog.entities.User;
import com.ContentMgtSystem.Blog.repositories.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/addNewPost")
    public String addNewPost(Post post, HttpServletRequest request) {
        int userID = Integer.parseInt(request.getParameter("user_id"));
        User user = userRepository.findById(userID).get();
        post.setUser(user);

        // If admin, set status as approved, save post, return to admin page
        if (user.getRoles().contains(roleRepository.findById(1).get())) {
            post.setPost_status(post_statusRepository.findById(2).get());
            postRepository.save(post);
            return "redirect:/admin";
        }
        // If user, set status as pending, save post, return to user page
        else if (user.getRoles().contains(roleRepository.findById(2).get())) {
            post.setPost_status(post_statusRepository.findById(1).get());
            postRepository.save(post);
            return "redirect:/user";
        }
        // If not valid user, do not save post, return to home page
        else {
            return "redirect:/";
        }
    }

    @GetMapping("/admin")
    public String adminPage(Model model){
        // Admin page should display all posts, regardless of status
        // Admin should be able to view and edit all posts, in addition to changing status
        model.addAttribute("posts",postRepository.findAll());
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

        post.setPost_status(post_statusRepository.findById(2).orElse(null));

        postRepository.save(post);

        return "redirect:/adminReview";
    }

    // User page functionality
    @GetMapping("user")
    public String userPage(int user_id, Model model) {
        User user = userRepository.findById(user_id).get();
        List<Post> userPosts = postRepository.findByUser(user);
        model.addAttribute("userPosts", userPosts);
        return "user";
    }

    @GetMapping("/editPost")
    public String editPost(int user_id, int post_id, Model model) {
        Post post = postRepository.findById(post_id).get();
        if (post.getUser().getUser_id() != user_id) {
            return "unauthorizedWarning";
        }
        model.addAttribute("post", post);
        return "editPost";
    }

    // this is janky, suggestions on better ways welcome
    @PostMapping("/editPost")
    public String performEditPost(Post post, BindingResult result, HttpServletRequest request) {
        int user_id = Integer.parseInt(request.getParameter("user_id"));
        int status_id = Integer.parseInt(request.getParameter("status_id"));
        User user = userRepository.findById(user_id).get();
        Post_Status post_status = post_statusRepository.getById(status_id);
        post.setPost_status(post_status);
        post.setUser(user);
        postRepository.save(post);
        return "redirect:/user?user_id=" + user_id;
    }

}
