package com.App.Yogesh.Dto;

import com.App.Yogesh.Models.Story;

import java.time.LocalDateTime;

public class StoryDto {
     private Integer id;
        private Integer userId;
       private String captions;
        private String image;
        private LocalDateTime timeStamp;
    public StoryDto(){};

    public StoryDto(Story story) {
        this.id = story.getId();
        this.userId = story.getUser().getId();
        this.captions = story.getCaptions();
        this.image = story.getImage();
        this.timeStamp = story.getTimeStamp();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaptions() {
        return captions;
    }

    public void setCaptions(String captions) {
        this.captions = captions;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
