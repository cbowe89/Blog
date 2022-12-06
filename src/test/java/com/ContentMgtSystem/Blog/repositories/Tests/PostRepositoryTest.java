package com.ContentMgtSystem.Blog.repositories.Tests;

import com.ContentMgtSystem.Blog.TestApplicationConfiguration;
import com.ContentMgtSystem.Blog.entities.*;
import com.ContentMgtSystem.Blog.repositories.*;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
@Transactional
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    Post_StatusRepository post_statusRepository;

    @Autowired
    TagRepository tagRepository;

    @Before
    public void setUp() throws Exception {
        postRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @Test
    public void testFindByUser() {
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
        Assertions.assertNotNull(listOfUsers);
        Assertions.assertEquals(3, listOfUsers.size());
    }


    @Test
    public void testFindAllPostsDescWithLimit(){
        // creating test post 1
        User user1 = new User();
        user1.setUsername("Test1");
        user1.setPassword("TestPass1");
        userRepository.save(user1);

        String title = "TestTitle1";
        String content = "TestContent1";
        String testTimestampCreated = "2022-01-01 11:11:11";
        Timestamp newTimeStamp1 = Timestamp.valueOf(testTimestampCreated);
        String testTimestampExpiration = "2022-08-09 10:10:10";
        Timestamp newTimeStamp2 = Timestamp.valueOf(testTimestampExpiration);

        Tag tag1 = new Tag();
        tag1.setTag_name("TestTag1");
        tagRepository.save(tag1);
        Tag tag2 = new Tag();
        tag2.setTag_name("TestTag2");
        tagRepository.save(tag2);

        List <Tag> allTags = tagRepository.findAll();

        Post post = new Post();
        post.setUser(user1);
        post.setTitle(title);
        post.setContent(content);
        post.setCreated_date(newTimeStamp1);
        post.setExpiration_date(newTimeStamp2);
        post.setPost_status(post_statusRepository.findById(2).get());

        post.setTags(allTags);

        postRepository.save(post);

        // create test post 2

        User user2 = new User();
        user2.setUsername("Test2");
        user2.setPassword("TestPass2");
        userRepository.save(user2);

        String title2 = "TestTitle2";
        String content2 = "TestContent2";
        String testTimestampCreated2 = "2022-05-05 11:11:11";
        Timestamp newTimeStamp12 = Timestamp.valueOf(testTimestampCreated2);
        String testTimestampExpiration2 = "2022-08-09 10:10:10";
        Timestamp newTimeStamp22 = Timestamp.valueOf(testTimestampExpiration2);

        Tag tag12 = new Tag();
        tag12.setTag_name("TestTag12");
        tagRepository.save(tag12);
        Tag tag22 = new Tag();
        tag22.setTag_name("TestTag22");
        tagRepository.save(tag22);

        List <Tag> allTags2 = tagRepository.findAll();

        Post post2 = new Post();
        post2.setUser(user2);
        post2.setTitle(title2);
        post2.setContent(content2);
        post2.setCreated_date(newTimeStamp12);
        post2.setExpiration_date(newTimeStamp22);
        post2.setPost_status(post_statusRepository.findById(2).get());

        post2.setTags(allTags2);

        postRepository.save(post2);

        List<Post> listOfPost = postRepository.findAll();

        Assertions.assertNotNull(listOfPost);
        Assertions.assertEquals(2, listOfPost.size());

        List<Post> listOfPostWithLimit = postRepository.findAllPostsDescWithLimit();

        Assertions.assertNotNull(listOfPostWithLimit);
        Assertions.assertEquals(1, listOfPostWithLimit.size());
        Assertions.assertTrue(listOfPostWithLimit.contains(post2));
    }

    @Test
    public void findAllOrderByCreated_dateDesc() {
        // creating test post 1
        User user1 = new User();
        user1.setUsername("Test1");
        user1.setPassword("TestPass1");
        userRepository.save(user1);

        String title = "TestTitle1";
        String content = "TestContent1";
        String testTimestampCreated = "2022-01-01 11:11:11";
        Timestamp newTimeStamp1 = Timestamp.valueOf(testTimestampCreated);
        String testTimestampExpiration = "2022-08-09 10:10:10";
        Timestamp newTimeStamp2 = Timestamp.valueOf(testTimestampExpiration);

        Tag tag1 = new Tag();
        tag1.setTag_name("TestTag1");
        tagRepository.save(tag1);
        Tag tag2 = new Tag();
        tag2.setTag_name("TestTag2");
        tagRepository.save(tag2);

        List<Tag> allTags = tagRepository.findAll();

        Post post = new Post();
        post.setUser(user1);
        post.setTitle(title);
        post.setContent(content);
        post.setCreated_date(newTimeStamp1);
        post.setExpiration_date(newTimeStamp2);
        
        post.setPost_status(post_statusRepository.findById(2).get());
        post.setTags(allTags);

        postRepository.save(post);

        // create test post 2

        User user2 = new User();
        user2.setUsername("Test2");
        user2.setPassword("TestPass2");
        userRepository.save(user2);

        String title2 = "TestTitle2";
        String content2 = "TestContent2";
        String testTimestampCreated2 = "2022-05-05 11:11:11";
        Timestamp newTimeStamp12 = Timestamp.valueOf(testTimestampCreated2);
        String testTimestampExpiration2 = "2022-08-09 10:10:10";
        Timestamp newTimeStamp22 = Timestamp.valueOf(testTimestampExpiration2);

        Tag tag12 = new Tag();
        tag12.setTag_name("TestTag12");
        tagRepository.save(tag12);
        Tag tag22 = new Tag();
        tag22.setTag_name("TestTag22");
        tagRepository.save(tag22);

        List <Tag> allTags2 = tagRepository.findAll();

        Post post2 = new Post();
        post2.setUser(user2);
        post2.setTitle(title2);
        post2.setContent(content2);
        post2.setCreated_date(newTimeStamp12);
        post2.setExpiration_date(newTimeStamp22);

        post2.setPost_status(post_statusRepository.findById(2).get());

        post2.setTags(allTags2);

        postRepository.save(post2);

        List<Post> listOfPost = postRepository.findAll();

        Timestamp testTimeStamp = Timestamp.valueOf("2000-01-01 00:00:00");
        for (Post _post: listOfPost) {
            Assertions.assertNotNull(_post);
            int timestampComparison = testTimeStamp.compareTo(_post.getCreated_date());
            Assertions.assertTrue(timestampComparison < 0);
            testTimeStamp = _post.getCreated_date();
        }

        Assertions.assertEquals(2, listOfPost.size());
        List<Post> listOfPostDescending = postRepository.findAllNotExpired();
        Assertions.assertEquals(2, listOfPostDescending.size());
    }

    @Test
    public void findAllByPending(){
        // creating test post 1
        User user1 = new User();
        user1.setUsername("Test1");
        user1.setPassword("TestPass1");
        userRepository.save(user1);

        String title = "TestTitle1";
        String content = "TestContent1";
        String testTimestampCreated = "2022-01-01 11:11:11";
        Timestamp newTimeStamp1 = Timestamp.valueOf(testTimestampCreated);
        String testTimestampExpiration = "2022-08-09 10:10:10";
        Timestamp newTimeStamp2 = Timestamp.valueOf(testTimestampExpiration);

        Tag tag1 = new Tag();
        tag1.setTag_name("TestTag1");
        tagRepository.save(tag1);
        Tag tag2 = new Tag();
        tag2.setTag_name("TestTag2");
        tagRepository.save(tag2);

        List <Tag> allTags = tagRepository.findAll();

        Post post = new Post();
        post.setUser(user1);
        post.setTitle(title);
        post.setContent(content);
        post.setCreated_date(newTimeStamp1);
        post.setExpiration_date(newTimeStamp2);
        post.setPost_status(post_statusRepository.findById(1).get());

        post.setTags(allTags);

        postRepository.save(post);

        // create test post 2

        User user2 = new User();
        user2.setUsername("Test2");
        user2.setPassword("TestPass2");
        userRepository.save(user2);

        String title2 = "TestTitle2";
        String content2 = "TestContent2";
        String testTimestampCreated2 = "2022-05-05 11:11:11";
        Timestamp newTimeStamp12 = Timestamp.valueOf(testTimestampCreated2);
        String testTimestampExpiration2 = "2022-08-09 10:10:10";
        Timestamp newTimeStamp22 = Timestamp.valueOf(testTimestampExpiration2);

        Tag tag12 = new Tag();
        tag12.setTag_name("TestTag12");
        tagRepository.save(tag12);
        Tag tag22 = new Tag();
        tag22.setTag_name("TestTag22");
        tagRepository.save(tag22);

        List <Tag> allTags2 = tagRepository.findAll();

        Post post2 = new Post();
        post2.setUser(user2);
        post2.setTitle(title2);
        post2.setContent(content2);
        post2.setCreated_date(newTimeStamp12);
        post2.setExpiration_date(newTimeStamp22);
        post2.setPost_status(post_statusRepository.findById(2).get());

        post2.setTags(allTags2);

        postRepository.save(post2);

        List<Post> listOfPost = postRepository.findAll();

        Assertions.assertNotNull(listOfPost);
        Assertions.assertEquals(2, listOfPost.size());

        List<Post> listOfPostWithLimit = postRepository.findAllByPending();

        Assertions.assertNotNull(listOfPostWithLimit);
        Assertions.assertEquals(1, listOfPostWithLimit.size());
        Assertions.assertTrue(listOfPostWithLimit.contains(post));

    }

}