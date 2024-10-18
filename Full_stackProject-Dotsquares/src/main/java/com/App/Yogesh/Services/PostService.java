package com.App.Yogesh.Services;

import com.App.Yogesh.Models.Post;

import java.util.List;

/**
 * Interface defining operations for managing posts.
 */
public interface PostService {

    /**
     * Creates a new post for a user.
     */
    Post createNewPost(Post post, Integer userId) throws Exception;

    /**
     * Deletes a post.
     */
    String deletePost(Integer postId, Integer userId) throws Exception;

    /**
     * Retrieves all posts made by a specific user.
     */
    List<Post> findPostByUserId(Integer userId);

    /**
     * Retrieves a post by its ID.
     */
    Post findPostById(Integer postId) throws Exception;

    /**
     * Retrieves all posts.
     */
    List<Post> findAllPost();

    /**
     * Saves a post.
     */
    Post savedPost(Integer postId, Integer userId) throws Exception;

    /**
     * Likes a post.
     */
    Post likePost(Integer postId, Integer userId) throws Exception;
}
