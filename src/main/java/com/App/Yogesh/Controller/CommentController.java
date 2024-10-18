package com.App.Yogesh.Controller;

import com.App.Yogesh.Dto.CommentDto;
import com.App.Yogesh.Dto.UserDetailsDto;
import com.App.Yogesh.Dto.UserDto;
import com.App.Yogesh.Models.Comment;
import com.App.Yogesh.Models.User;
import com.App.Yogesh.Response.ApiResponse;
import com.App.Yogesh.Services.CommentService;
import com.App.Yogesh.Services.UserService;
import com.App.Yogesh.config.UserContext;
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

    @Autowired
    UserContext userContext;



    //Api for Creating Comment
    @PostMapping("/api/comments/post/{postId}")
    public ResponseEntity<ApiResponse<?>> createComment(@RequestBody Comment comment,@PathVariable("postId") Integer postId) throws Exception {
     UserDetailsDto currentUser = userContext.getCurrentUser();
        User reqUser = userService.findUserByEmail(currentUser.getEmail());
        if (reqUser == null) {
            throw new Exception("User not found ");
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

    //Api for linking Comment
    @PostMapping("/api/comments/like/{commentId}")
    public ResponseEntity<ApiResponse<?>> likeComment(@PathVariable("commentId") Integer commentId) throws Exception {

        UserDetailsDto currentUser = userContext.getCurrentUser();
        User reqUser = userService.findUserByEmail(currentUser.getEmail());
        // Ensure the user is found with the provided
        if (reqUser == null) {
            throw new Exception("User not found ");
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
