package com.ContentMgtSystem.Blog.repositories.Tests;

import com.ContentMgtSystem.Blog.entities.Post;
import com.ContentMgtSystem.Blog.entities.Role;
import com.ContentMgtSystem.Blog.entities.User;
import com.ContentMgtSystem.Blog.repositories.PostRepository;
import com.ContentMgtSystem.Blog.repositories.RoleRepository;
import com.ContentMgtSystem.Blog.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Before
    public void setUp() throws Exception {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        postRepository.deleteAll();
//        List<User> users = userRepository.findAll();
//        for (User user : users) {
//            userRepository.deleteById(user.getUser_id());
//        }
//        List<Role> roles = roleRepository.findAll();
//        for (Role role : roles) {
//            roleRepository.deleteById(role.getRole_id());
//        }
//        List<Post> posts = postRepository.findAll();
//        for (Post post : posts) {
//            postRepository.deleteById(post.getPost_id());
//        }
    }

    @Test
    public void findByUser() {
        User user1 = new User();
        user1.setUsername("Test1");
        user1.setPassword("TestPass1");
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("Test2");
        user2.setPassword("TestPass2");
        userRepository.save(user2);

        User user3 = new User();
        user3.setUsername("Test3");
        user3.setPassword("TestPass3");
        userRepository.save(user3);

        List<User> listOfUsers = userRepository.findAll();

        Role role = new Role();
        role.setRole_name("admin");
        role.setUsers(listOfUsers);
        roleRepository.save(role);

        List<User> allFromRole = roleRepository.findById(role.getRole_id()).get().getUsers();

        Assertions.assertNotNull(allFromRole);
        Assertions.assertEquals(3, allFromRole.size());
    }

    @Test
    public void findAllPostsDescWithLimit() {
    }

    @Test
    public void findAllOrderByCreated_dateDesc() {
    }

    @Test
    public void findAllByPending() {
    }
}