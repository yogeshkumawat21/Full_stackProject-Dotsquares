package com.App.Yogesh.Dto;

import com.App.Yogesh.Models.Reels;

public class ReelDto {
    private Integer id;
    private String title;
    private Integer userId;
    private String video;

    public ReelDto(){};
    public ReelDto(Reels reel) {
        this.id = reel.getId();
        this.title = reel.getTitle();
        this.userId = reel.getUser().getId();
        this.video = reel.getVideo();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "ReelDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", userId=" + userId +
                ", video=" + video +
                '}';
    }
}
