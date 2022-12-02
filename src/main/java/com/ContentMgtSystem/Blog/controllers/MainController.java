package com.ContentMgtSystem.Blog.controllers;

import com.ContentMgtSystem.Blog.repositories.PostRepository;
import com.ContentMgtSystem.Blog.repositories.RoleRepository;
import com.ContentMgtSystem.Blog.repositories.TagRepository;
import com.ContentMgtSystem.Blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
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
        model.addAttribute("posts", postRepository.findAll());
        return "index";
    }
}
