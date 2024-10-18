package com.App.Yogesh.Controller;

import com.App.Yogesh.Dto.CommentDto;
import com.App.Yogesh.Dto.UserDto;
import com.App.Yogesh.Models.Comment;
import com.App.Yogesh.Models.User;
import com.App.Yogesh.Response.ApiResponse;
import com.App.Yogesh.Services.CommentService;
import com.App.Yogesh.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/comments/post/{postId}")
    public ResponseEntity<ApiResponse<?>> createComment(@RequestBody Comment comment,
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
        if (comment.getContent() == null) {
            ApiResponse<UserDto> response = new ApiResponse<>(
                    "Comment can't be empty",
                    HttpStatus.NO_CONTENT.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Comment createdComment = commentService.createComment(comment, postId, reqUser.getId());
        CommentDto commentDto = new CommentDto(createdComment);
        ApiResponse<CommentDto> response = new ApiResponse<>(
                "Comment created successfully",
                HttpStatus.CREATED.value(),
                commentDto
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/comments/like/{commentId}")
    public ResponseEntity<ApiResponse<?>> likeComment(
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
        CommentDto commentDto = new CommentDto(likedComment); // Create a DTO for the liked comment

        ApiResponse<CommentDto> response = new ApiResponse<>(
                "Comment liked successfully",
                HttpStatus.OK.value(),
                commentDto
        );
        return ResponseEntity.ok(response);
    }
}
