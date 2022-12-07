package com.ContentMgtSystem.Blog.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
public class Post {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int post_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Timestamp created_date;

    @Column
    private Timestamp expiration_date;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private Post_Status post_status;

    @ManyToMany
    @JoinTable(name = "post_tag",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags;

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Timestamp created_date) {
        this.created_date = created_date;
    }

    public Timestamp getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Timestamp expiration_date) {
        this.expiration_date = expiration_date;
    }

    public Post_Status getPost_status() {
        return post_status;
    }

    public void setPost_status(Post_Status post_status) {
        this.post_status = post_status;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return post_id == post.post_id
                && Objects.equals(user, post.user)
                && Objects.equals(title, post.title)
                && Objects.equals(content, post.content)
                && Objects.equals(created_date, post.created_date)
                && Objects.equals(expiration_date, post.expiration_date)
                && Objects.equals(post_status, post.post_status)
                && Objects.equals(tags, post.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post_id, user, title, content, created_date,
                expiration_date, post_status, tags);
    }
}
