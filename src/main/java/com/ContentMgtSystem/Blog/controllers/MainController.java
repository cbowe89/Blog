package com.ContentMgtSystem.Blog.controllers;

import com.ContentMgtSystem.Blog.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/content")
    public String viewAllPosts(Model model) {
        model.addAttribute("posts",
                postRepository.findAllByOrderByCreated_dateDesc());
        return "content";
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

    @GetMapping("approvePost")
    public String approvePost(int post_id){
        postRepository.changePostStatus(post_id);
        return "redirect:/admin";
    }

}
