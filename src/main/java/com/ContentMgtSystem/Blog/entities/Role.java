package com.ContentMgtSystem.Blog.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Role {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int role_id;

    @Column(nullable = false)
    private String role_name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return role_id == role.role_id
                && Objects.equals(role_name, role.role_name)
                && Objects.equals(users, role.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role_id, role_name, users);
    }
}
