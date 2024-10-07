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
    public Comment createComment(@RequestBody Comment comment , @PathVariable("postId") Integer postId, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwt(jwt);
        Comment Createdcomment = commentService.createComment(comment ,postId ,user.getId());

        return Createdcomment;
    }
}
