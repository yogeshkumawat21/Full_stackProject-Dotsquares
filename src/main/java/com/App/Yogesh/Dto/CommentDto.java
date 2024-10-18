package com.App.Yogesh.Dto;

import com.App.Yogesh.Models.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommentDto {
    private Integer id;
    private String content;
    private Integer userId;
    private List<Integer> likedUserIds;
    private LocalDateTime createdAt;
    private Integer postId;

    // Default constructor
    public CommentDto() {
        this.likedUserIds = new ArrayList<>();
    }

    // Constructor that takes a Comment object
    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userId = comment.getUser() != null ? comment.getUser().getId() : null;
        this.likedUserIds = comment.getLiked().stream()
                .map(user -> user.getId())
                .collect(Collectors.toList());
        this.createdAt = comment.getCreatedAt();
        this.postId = comment.getPost() != null ? comment.getPost().getId() : null;
    }

    // Getters and Setters
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getLikedUserIds() {
        return likedUserIds;
    }

    public void setLikedUserIds(List<Integer> likedUserIds) {
        this.likedUserIds = likedUserIds;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", likedUserIds=" + likedUserIds +
                ", createdAt=" + createdAt +
                ", postId=" + postId +
                '}';
    }
}
