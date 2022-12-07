package com.ContentMgtSystem.Blog.repositories.Tests;

import com.ContentMgtSystem.Blog.TestApplicationConfiguration;
import com.ContentMgtSystem.Blog.entities.User;
import com.ContentMgtSystem.Blog.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
@Transactional
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void findByUsername() {
        User user = new User();
        user.setUsername("Test");
        user.setPassword("TestPass");
        userRepository.save(user);

        User user1 = userRepository.findByUsername(user.getUsername());

        Assertions.assertEquals(user.getUsername(), user1.getUsername());
        Assertions.assertEquals(user.getPassword(), user1.getPassword());
    }
}