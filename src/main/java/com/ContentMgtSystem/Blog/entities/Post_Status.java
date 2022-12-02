package com.ContentMgtSystem.Blog.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Post_Status {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int status_id;

    @Column(nullable = false)
    private String status_name;

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post_Status that = (Post_Status) o;
        return status_id == that.status_id
                && Objects.equals(status_name, that.status_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status_id, status_name);
    }
}
