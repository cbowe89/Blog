package com.ContentMgtSystem.Blog.controllers;

import com.ContentMgtSystem.Blog.entities.Post;
import com.ContentMgtSystem.Blog.entities.Post_Status;
import com.ContentMgtSystem.Blog.entities.Tag;
import com.ContentMgtSystem.Blog.entities.Role;
import com.ContentMgtSystem.Blog.entities.User;
import com.ContentMgtSystem.Blog.repositories.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    ImageRepository imageRepository;

    // Helper function to get user cookie
    private Optional<String> fetchCookie(HttpServletRequest request) {
        if (request.getCookies() == null || request.getCookies().length == 0) {
            return Optional.empty();
        }
        return Arrays.stream(request.getCookies())
                .filter(cookie->"user_id".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findAny();
    }

    private String navDisplay(Model model, HttpServletRequest request) {
        Optional<String> userCookie = fetchCookie(request);
        String logInStatus = "out";
        if (userCookie.isPresent()) {
            logInStatus = "in";
            int user_id = Integer.parseInt(userCookie.get());
            User user = userRepository.findById(user_id).get();
            String role = "aUser";
            if (user.getRoles()
                    .stream()
                    .filter(role1 -> role1.getRole_name().equals("admin")).count() == 1) {
                role = "anAdmin";
            }
            model.addAttribute("role", role);
        }
        model.addAttribute("logInStatus", logInStatus);
        return "navbar";
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        model.addAttribute("recentPosts", postRepository.findAllPostsDescWithLimit());
        navDisplay(model, request);
        return "homepage";
    }

    @GetMapping("/disclaimer")
    public String disclaimer(HttpServletRequest request, Model model) {
        navDisplay(model, request);
        return "disclaimer";
    }

    @GetMapping("/content")
    public String viewAllPosts(HttpServletRequest request, Model model) {
        model.addAttribute("posts",
                postRepository.findAllNotExpired());
        model.addAttribute("tags", tagRepository.findAll());
        navDisplay(model, request);
        return "content";
    }

    @GetMapping("/writePost")
    public String writePost(HttpServletRequest request, Model model) {
        Optional<String> userCookie = fetchCookie(request);
        if (userCookie.isEmpty()) {
            return "redirect:/login";
        }
        navDisplay(model, request);
        return "writePost";
    }

    @PostMapping("/addNewPost")
    public String addNewPost(Post post, HttpServletRequest request) {
        Optional<String> userCookie = fetchCookie(request);
        if (userCookie.isEmpty()) {
            return "redirect:/login";
        }

        // Get user object based on cookie, set User for post
        int user_id = Integer.parseInt(userCookie.get());
        User user = userRepository.findById(user_id).get();
        post.setUser(user);

        // Set created date as date post is submitted
        post.setCreated_date(Timestamp.valueOf(LocalDateTime.now()));

        // Set tags
        String tagsFromHtml = request.getParameter("tagsFromHtml");
        if (tagsFromHtml != null) {
            String[] tagArray = tagsFromHtml.split(",");
            List<Tag> thisPostTags = new ArrayList<>();
            for (String tagString : tagArray) {
                if (tagRepository.findStringByContent(tagString) == null) {
                    Tag newTag = new Tag();
                    newTag.setTag_name(tagString);
                    thisPostTags.add(newTag);
                    tagRepository.save(newTag);
                }
                else
                    thisPostTags.add(tagRepository.findTagByContent(tagString));
            }
            if (!thisPostTags.isEmpty())
                post.setTags(thisPostTags);
        }

        // If expiration date is set, change from String to Timestamp and set
        String htmlExpDate = request.getParameter("htmlExpDate");
        if (!htmlExpDate.equals("")) {
            String dateTimeString = htmlExpDate.replace("T", " ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.parse(dateTimeString, formatter));
            post.setExpiration_date(timestamp);
        }

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
    public String adminPage(Model model, HttpServletRequest request){
        Optional<String> userCookie = fetchCookie(request);
        if (userCookie.isEmpty()) {
            return "redirect:/login";
        }
        navDisplay(model, request);
        // Admin page should display all posts, regardless of status
        // Admin should be able to view and edit all posts, in addition to changing status
        model.addAttribute("posts",postRepository.findAll());
        return "admin";
    }

    @GetMapping("deletePost")
    public String deletePost(int post_id, Model model, HttpServletRequest request){
        Optional<String> userCookie = fetchCookie(request);
        if (userCookie.isEmpty()) {
            return "redirect:/login";
        }
        navDisplay(model, request);
        postRepository.deleteById(post_id);
        return "redirect:/admin";
    }

    @GetMapping("adminReview")
    public String adminReviewPage(Model model, HttpServletRequest request){
        Optional<String> userCookie = fetchCookie(request);
        if (userCookie.isEmpty()) {
            return "redirect:/login";
        }
        navDisplay(model, request);
        model.addAttribute("pendings",postRepository.findAllByPending());
        return "adminReview";
    }

    @GetMapping("approvePost") // GetMapping correct here
    public String approvePost(int post_id, Model model, HttpServletRequest request){
        Optional<String> userCookie = fetchCookie(request);
        if (userCookie.isEmpty()) {
            return "redirect:/login";
        }
        navDisplay(model, request);
        Post post = postRepository.findById(post_id).get();

        post.setPost_status(post_statusRepository.findById(2).orElse(null));

        postRepository.save(post);

        return "redirect:/adminReview";
    }

    // User page functionality
    @GetMapping("user")
    public String userPage(HttpServletRequest request, Model model) {
        Optional<String> userCookie = fetchCookie(request);
        if (!userCookie.isPresent()) {
            return "redirect:/login";
        }
        navDisplay(model, request);
        int user_id = Integer.parseInt(userCookie.get());
        User user = userRepository.findById(user_id).get();
        List<Post> userPosts = postRepository.findByUser(user);
        model.addAttribute("userPosts", userPosts);
        return "user";
    }

    @GetMapping("/editPost")
    public String editPost(int post_id, Model model, HttpServletRequest request) {
        Optional<String> userCookie = fetchCookie(request);
        Post post = postRepository.findById(post_id).get();
        if (!userCookie.isPresent()) {
            return "redirect:/login";
        }
        navDisplay(model, request);
        int user_id = Integer.parseInt(userCookie.get());
        if (post.getUser().getUser_id() != user_id) {
            return "unauthorizedWarning";
        }
        model.addAttribute("post", post);
        return "editPost";
    }

    // this is janky, suggestions on better ways welcome
    @PostMapping("/editPost")
    public String performEditPost(Post post, Model model, HttpServletRequest request) {
        Optional<String> userCookie = fetchCookie(request);
        if (!userCookie.isPresent()) {
            return "redirect:/login";
        }
        navDisplay(model, request);
        int user_id = Integer.parseInt(userCookie.get());
        int status_id = Integer.parseInt(request.getParameter("status_id"));
        User user = userRepository.findById(user_id).get();
        Post_Status post_status = post_statusRepository.getById(status_id);
        post.setPost_status(post_status);
        post.setUser(user);
        postRepository.save(post);
        return "redirect:/user?user_id=" + user_id;
    }

    //Login page and logging in

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        Optional<String> userCookie = fetchCookie(request);
        if (userCookie.isPresent()) {
            return "redirect:/";
        }
        return "login";
    }

    @PostMapping("/login")
    public String performLogin(User user, HttpServletResponse response) {
        String username = user.getUsername();
        String password = user.getPassword();
        User userFromDatabase = userRepository.findByUsername(username);
        if (!password.equals(userFromDatabase.getPassword())) {
            return "redirect:/login";
        }
        Cookie jwtTokenCookie = new Cookie("user_id", String.valueOf(userFromDatabase.getUser_id()));
        jwtTokenCookie.setMaxAge(86400);
        jwtTokenCookie.setSecure(true);
        jwtTokenCookie.setHttpOnly(true);
        jwtTokenCookie.setDomain("");

        response.addCookie(jwtTokenCookie);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String performLogout(HttpServletResponse response, HttpServletRequest request) {
        Optional<String> userCookie = fetchCookie(request);
        if (userCookie.isPresent()) {
            Cookie deleteServletCookie = new Cookie("user_id", null);
            deleteServletCookie.setMaxAge(0);
            response.addCookie(deleteServletCookie);
        }
        return "redirect:/";
    }

}
