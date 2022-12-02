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

    @GetMapping("/writePost")
    public String writePost() {
        return "writePost";
    }
}
