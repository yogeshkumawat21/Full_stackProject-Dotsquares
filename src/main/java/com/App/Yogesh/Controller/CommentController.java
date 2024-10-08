package com.App.Yogesh.Controller;

import com.App.Yogesh.Models.Comment;
import com.App.Yogesh.Models.User;
import com.App.Yogesh.Services.CommentService;
import com.App.Yogesh.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/comments/post/{postId}")
    public Comment createComment(@RequestBody Comment comment,
                                 @PathVariable("postId") Integer postId,
                                 @RequestHeader("Authorization") String jwt) throws Exception {
        // Validate the Authorization header
        if (jwt == null || !jwt.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        // Extract the JWT token by removing the "Bearer " prefix and trimming spaces
        String token = jwt.substring(7).trim();

        // Fetch the user using the token
        User reqUser = userService.findUserByJwt(token);

        // Ensure the user is found with the provided JWT
        if (reqUser == null) {
            throw new Exception("User not found for the provided JWT");
        }

        // Create the comment with the found user ID
        Comment createdComment = commentService.createComment(comment, postId, reqUser.getId());

        return createdComment;
    }


    @PostMapping("/api/comments/like/{commentId}")
    public Comment likeComment(
            @PathVariable("commentId") Integer commentId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        // Validate the JWT token format
        if (jwt == null || !jwt.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        // Extract the JWT token by removing the "Bearer " prefix
        String token = jwt.substring(7).trim();
        User reqUser = userService.findUserByJwt(token); // Use token here

        // Ensure the user is found with the provided JWT
        if (reqUser == null) {
            throw new Exception("User not found for the provided JWT");
        }

        // Like the comment by the authenticated user
        Comment likedComment = commentService.likeComment(commentId, reqUser.getId());

        return likedComment;
    }}
