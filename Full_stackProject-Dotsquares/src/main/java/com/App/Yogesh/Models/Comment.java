package com.App.Yogesh.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Change to IDENTITY for auto-increment
    private Integer id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id") // Foreign key column for user
    private User user;

    @ManyToMany
    private List<User> liked = new ArrayList<>();

    private LocalDateTime createdAt;

    @ManyToOne // Each comment should belong to one post
    @JoinColumn(name = "post_id") // Foreign key column for post
    private Post post;

    public Comment() {
        this.createdAt = LocalDateTime.now(); // Initialize createdAt with the current time
    }

    public Comment(Integer id, String content, List<User> liked, LocalDateTime createdAt, User user, Post post) {
        this.id = id;
        this.content = content;
        this.liked = liked;
        this.createdAt = createdAt;
        this.user = user;
        this.post = post;
    }

    // Getters and setters...

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getLiked() {
        return liked;
    }

    public void setLiked(List<User> liked) {
        this.liked = liked;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
