/**
 * Represents a social media post with associated details such as image, caption, video,
 * the user who created the post, creation timestamp, and a list of users who liked the post.
 */



package com.App.Yogesh.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post {

    public  Post(){};
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String image;
    private String Caption;
    private String video ;

    @ManyToOne
    private  User user ;
    private LocalDateTime createdAt;

    @OneToMany
    private List<User> liked = new ArrayList<>();

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany
    private List<Comment> comments = new ArrayList<>();



    public Post(Integer id, List<Comment> comments,String image, String caption, String video, User user, LocalDateTime createdAt, List<User> liked) {
        super();
        this.comments = comments;
        this.id = id;
        this.image = image;
        this.Caption = caption;
        this.video = video;
        this.user = user;
        this.createdAt = createdAt;
        this.liked = liked;
    }

    public List<User> getLiked() {
        return liked;
    }

    public void setLiked(List<User> liked) {
        this.liked = liked;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }



}
