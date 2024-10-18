package com.App.Yogesh.Dto;

import com.App.Yogesh.Models.Post;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostDto {
    private Integer id;
    private String image;
    private String caption;
    private String video;
    private List<UserDto> liked;
    private List<CommentDto> comments; // Change Comment to CommentDto
    private LocalDateTime createdAt;
    private Integer userId; // You can keep this or use UserDto

    public PostDto(Post post) {
        this.id = post.getId();
        this.image = post.getImage();
        this.caption = post.getCaption();
        this.video = post.getVideo();
        this.liked = post.getLiked().stream().map(UserDto::new).collect(Collectors.toList());
        this.comments = post.getComments().stream().map(CommentDto::new).collect(Collectors.toList()); // Convert to CommentDto
        this.createdAt = post.getCreatedAt();
        this.userId = post.getUser() != null ? post.getUser().getId() : null; // Get user ID safely
    }

    public PostDto() {
        this.liked = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    // Getters and Setters...

    public List<UserDto> getLiked() {
        return liked;
    }

    public void setLiked(List<UserDto> liked) {
        this.liked = liked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUserId() {
        return userId; // Add getter for userId
    }

    public void setUserId(Integer userId) {
        this.userId = userId; // Add setter for userId
    }

    @Override
    public String toString() {
        return "PostDto{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", caption='" + caption + '\'' +
                ", video='" + video + '\'' +
                ", liked=" + liked +
                ", comments=" + comments +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                '}';
    }
}
