package com.ContentMgtSystem.Blog.repositories.Tests;

import com.ContentMgtSystem.Blog.TestApplicationConfiguration;
import com.ContentMgtSystem.Blog.entities.Tag;
import com.ContentMgtSystem.Blog.repositories.PostRepository;
import com.ContentMgtSystem.Blog.repositories.TagRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
@Transactional
public class TagRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    TagRepository tagRepository;

    @Before
    public void setUp() throws Exception {
        tagRepository.deleteAll();
    }

    @Test
    public void findStringByContent() {
        Tag tag1 = new Tag();
        tag1.setTag_name("TestTag1");
        tagRepository.save(tag1);
        Tag tag2 = new Tag();
        tag2.setTag_name("TestTag2");
        tagRepository.save(tag2);

        Tag tag = tagRepository.findById(tag1.getTag_id()).get();
        Assertions.assertEquals("TestTag1", tag1.getTag_name());
    }

    @Test
    public void findTagByContent() {
        Tag tag1 = new Tag();
        tag1.setTag_name("TestTag1");
        tagRepository.save(tag1);
        Tag tag2 = new Tag();
        tag2.setTag_name("TestTag2");
        tagRepository.save(tag2);

        Tag tag = tagRepository.findById(tag1.getTag_id()).get();
        Assertions.assertEquals("TestTag1", tag1.getTag_name());
        Assertions.assertEquals(tag1.getTag_id(), tag.getTag_id());
    }

}