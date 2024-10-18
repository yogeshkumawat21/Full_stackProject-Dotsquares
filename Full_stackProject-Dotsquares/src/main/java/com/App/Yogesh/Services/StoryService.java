package com.App.Yogesh.Services;

import com.App.Yogesh.Models.Story;
import com.App.Yogesh.Models.User;

import java.util.List;

public interface StoryService {
    public Story createStory(Story story , User user);
    public List<Story> findStoryByUserId(Integer userId) throws Exception;
}
