package com.ContentMgtSystem.Blog.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Image {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int image_id;

    @Column(nullable = false)
    private String image_path;

    @ManyToMany(mappedBy = "images")
    private List<Post> posts;

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return image_id == image.image_id
                && Objects.equals(image_path, image.image_path)
                && Objects.equals(posts, image.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(image_id, image_path, posts);
    }
}
