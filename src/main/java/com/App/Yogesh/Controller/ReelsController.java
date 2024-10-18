package com.App.Yogesh.Controller;


import com.App.Yogesh.Dto.ReelDto;
import com.App.Yogesh.Dto.UserDetailsDto;
import com.App.Yogesh.Dto.UserDto;
import com.App.Yogesh.Models.Reels;
import com.App.Yogesh.Models.User;
import com.App.Yogesh.Response.ApiResponse;
import com.App.Yogesh.Services.ReelsService;
import com.App.Yogesh.Services.UserService;
import com.App.Yogesh.config.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReelsController {
    @Autowired
    private ReelsService reelsService;

    @Autowired
    UserService userService;

    @Autowired
    UserContext userContext;


    //Api for Creating Reels
    @PostMapping("/api/reels")
    public ResponseEntity<ApiResponse<?>> createReels(@RequestBody Reels reel ) throws Exception {
        UserDetailsDto currentUser = userContext.getCurrentUser();
        User reqUser = userService.findUserByEmail(currentUser.getEmail());
        // Ensure the user is found with the provided JWT
        if (reel.getVideo() == null) {
            ApiResponse<UserDto> response = new ApiResponse<>(
                    "Select Video to Upload",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Reels createdReels = reelsService.createReels(reel, reqUser);
        ReelDto reelDto = new ReelDto(createdReels);
        ApiResponse<ReelDto> response = new ApiResponse<>(
                "Reels Created Successfully",
                HttpStatus.CREATED.value(),
                reelDto
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //Find all reels
    @GetMapping("/api/reels")
    public ResponseEntity<ApiResponse<?>> findAllReels( ) throws Exception {

        List<Reels> reels = reelsService.findAllReels();

        if (reels.isEmpty()) {
            ApiResponse<UserDto> response = new ApiResponse<>(
                    "No Reel Found",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        // Map User entities to UserDto
        List<ReelDto> reelDtos = reels.stream()
                .map(ReelDto::new) // Using the constructor that takes a User object
                .collect(Collectors.toList());
        ApiResponse<List<ReelDto>> response = new ApiResponse<>(
                "Reel Fetched Successfully",
                HttpStatus.FOUND.value(),
                reelDtos
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


//find all reels for specific user id
    @GetMapping("/api/reels/{userId}")
    public ResponseEntity<ApiResponse<?>> findAllReels(@PathVariable Integer userId) throws Exception {

        List<Reels> reels = reelsService.findUsersReel(userId);
        if (reels.isEmpty()) {
            ApiResponse<UserDto> response = new ApiResponse<>(
                    "No Reel Found",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        // Map User entities to UserDto
        List<ReelDto> reelDtos = reels.stream()
                .map(ReelDto::new) // Using the constructor that takes a User object
                .collect(Collectors.toList());
        ApiResponse<List<ReelDto>> response = new ApiResponse<>(
                "Reel Fetched Successfully",
                HttpStatus.FOUND.value(),
                reelDtos
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
