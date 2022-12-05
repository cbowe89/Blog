package com.ContentMgtSystem.Blog.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Tag {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int tag_id;

    @Column(nullable = false)
    private String tag;

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts;

    public int getId() {
        return tag_id;
    }

    public void setId(int tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Post> getBlogPosts() {
        return posts;
    }

    public void setBlogPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag1 = (Tag) o;
        return tag_id == tag1.tag_id
                && Objects.equals(tag, tag1.tag)
                && Objects.equals(posts, tag1.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag_id, tag, posts);
    }
}
