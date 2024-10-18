package com.App.Yogesh.Controller;

import com.App.Yogesh.Dto.ReelDto;
import com.App.Yogesh.Dto.StoryDto;
import com.App.Yogesh.Dto.UserDetailsDto;
import com.App.Yogesh.Dto.UserDto;
import com.App.Yogesh.Models.Reels;
import com.App.Yogesh.Models.Story;
import com.App.Yogesh.Models.User;
import com.App.Yogesh.Response.ApiResponse;
import com.App.Yogesh.Services.StoryService;
import com.App.Yogesh.Services.UserService;
import com.App.Yogesh.config.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class StoryController {

    @Autowired
    private StoryService storyService;


    @Autowired
    private UserService userService;

    @Autowired
    UserContext userContext;

    //Api for Creating Story
    @PostMapping("/api/story")
    public ResponseEntity<ApiResponse<?>> createStory(@RequestBody Story story ) throws Exception {
        UserDetailsDto currentUser = userContext.getCurrentUser();
        User reqUser = userService.findUserByEmail(currentUser.getEmail());
        if (story.getCaptions()==null ||story.getImage()==null ) {
            ApiResponse<StoryDto> response = new ApiResponse<>(
                    "Fill Required  To Create Story",
                    HttpStatus.NOT_ACCEPTABLE.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Story createdStory = storyService.createStory(story,reqUser);
        StoryDto storyDto = new StoryDto(createdStory);
        ApiResponse<StoryDto> response = new ApiResponse<>(
                "Story created SuccessFully",
                HttpStatus.CREATED.value(),
                storyDto
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);


    }

    //find Story by UserId
    @GetMapping("/api/story/user/{userId}")
    public ResponseEntity<ApiResponse<?>> findUserStory(@PathVariable Integer userId) throws Exception {
        UserDetailsDto currentUser = userContext.getCurrentUser();
        User reqUser = userService.findUserByEmail(currentUser.getEmail());
       List<Story>  stories = storyService.findStoryByUserId(userId);
     if (stories.isEmpty()) {
            ApiResponse<UserDto> response = new ApiResponse<>(
                    "No Story Found",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<StoryDto> storyDtos = stories.stream()
                .map(StoryDto::new) // Using the constructor that takes a User object
                .collect(Collectors.toList());
        ApiResponse<List<StoryDto>> response = new ApiResponse<>(
                "Story Fetched Successfully",
                HttpStatus.FOUND.value(),
                storyDtos
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}
