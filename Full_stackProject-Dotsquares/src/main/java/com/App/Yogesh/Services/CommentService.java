package com.App.Yogesh.Services;

import com.App.Yogesh.Models.Comment;

public interface CommentService {

    public Comment createComment(Comment comment , Integer postId , Integer userId) throws Exception;
    public Comment likeComment (Integer CommentId , Integer userId) throws Exception;
    public Comment findCommentById(Integer commentId) throws Exception;
}
