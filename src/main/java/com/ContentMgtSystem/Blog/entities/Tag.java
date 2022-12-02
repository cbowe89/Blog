package com.ContentMgtSystem.Blog.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Tag {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(nullable = false)
    private String tag;

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return id == tag1.id
                && Objects.equals(tag, tag1.tag)
                && Objects.equals(posts, tag1.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tag, posts);
    }
}
