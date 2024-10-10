package com.App.Yogesh.Controller;


import com.App.Yogesh.Models.Reels;
import com.App.Yogesh.Models.User;
import com.App.Yogesh.Services.ReelsService;
import com.App.Yogesh.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReelsController {
    @Autowired
    private ReelsService reelsService;

    @Autowired
    UserService userService;


    @PostMapping("/api/reels")
    public Reels createReels(@RequestBody Reels reel , @RequestHeader("Authorization")String jwt) throws Exception {
        if (jwt == null || !jwt.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        // Extract the JWT token by removing the "Bearer " prefix
        String token = jwt.substring(7).trim();
        System.out.println(jwt);
        User reqUser = userService.findUserByJwt(token);

        // Ensure the user is found with the provided JWT
        if (reqUser == null) {
            throw new Exception("User not found for the provided JWT");
        }
        if (reel.getTitle()==null ||reel.getVideo()==null ) {
            throw new IllegalArgumentException("Enter the Mandatory  inputs");
        }
        Reels createdReels = reelsService.createReels(reel, reqUser);
        return createdReels;
    }

    @GetMapping("/api/reels")
    public List<Reels> findAllReels( ) throws Exception {

        List<Reels> reels = reelsService.findAllReels();
        return  reels;
    }

    @GetMapping("/api/reels/{userId}")
    public List<Reels> findAllReels(@PathVariable Integer userId) throws Exception {

        List<Reels> reels = reelsService.findUsersReel(userId);
        return  reels;
    }

}
