package com.ContentMgtSystem.Blog.controllers;

import com.ContentMgtSystem.Blog.entities.Post;
import com.ContentMgtSystem.Blog.entities.Post_Status;
import com.ContentMgtSystem.Blog.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("posts", postRepository.findAllByTimestamp());
        return "index";
    }

    @GetMapping("/writePost")
    public String writePost() {
        return "writePost";
    }

    @GetMapping("/admin")
    public String adminPage(Model model){
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

    @GetMapping("approvePost")
    public String approvePost(int post_id){
        Post post = postRepository.findById(post_id).get();

        post.getPost_status().setStatus_name("approved");

        postRepository.save(post);

        return "redirect:/adminReview";
    }

}
