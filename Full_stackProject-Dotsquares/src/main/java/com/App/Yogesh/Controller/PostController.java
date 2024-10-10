package com.App.Yogesh.Controller;

import com.App.Yogesh.Models.Post;
import com.App.Yogesh.Models.User;
import com.App.Yogesh.Response.ApiResponse;
import com.App.Yogesh.Services.PostService;
import com.App.Yogesh.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
  private   UserService userService;
    /**
     * Creates a new post for a specific user.
     */
    @PostMapping("/api/post")
    public ResponseEntity<Post> createPost(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody Post post) throws Exception {
        String jwt;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7).trim(); // Remove "Bearer " prefix
        } else {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        User reqUser = userService.findUserByJwt(jwt);
        if (post.getCaption() == null ) {
            throw new IllegalArgumentException("Enter something to Create Post");
        }
        Post createdPost = postService.createNewPost(post, reqUser.getId());
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);

    }

    /**
     * Deletes a specific post for a user.
     */
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(
            @PathVariable Integer postId
            , @RequestHeader ("Authorization") String jwt) throws Exception {
        if (jwt == null || !jwt.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        // Extract the JWT token by removing the "Bearer " prefix
        String token = jwt.substring(7).trim();
        User reqUser = userService.findUserByJwt(token);

        // Ensure the user is found with the provided JWT
        if (reqUser == null) {
            throw new Exception("User not found for the provided JWT");
        }

        String message = postService.deletePost(postId, reqUser.getId());
        ApiResponse response = new ApiResponse(message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves a post by its ID.
     */
    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> findPostByIdHandler(@PathVariable Integer postId) throws Exception {
        Post post = postService.findPostById(postId);
        return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
    }

    /**
     * Retrieves all posts made by a specific user.
     */
    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<Post>> findUsersPost(@PathVariable Integer userId) {
        List<Post> posts = postService.findPostByUserId(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);

    }

    /**
     * Retrieves all posts.
     */
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> findAllPost() {
        List<Post> posts = postService.findAllPost();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    /**
     * Saves a post for a specific user (e.g., bookmark or favorite).
     */
    @PutMapping("/posts/save/{postId}")
    public ResponseEntity<Post> savePostHandler(@PathVariable Integer postId
            , @RequestHeader ("Authorization") String jwt) throws Exception {
        if (jwt == null || !jwt.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        // Extract the JWT token by removing the "Bearer " prefix
        String token = jwt.substring(7).trim();
        User reqUser = userService.findUserByJwt(token);

        // Ensure the user is found with the provided JWT
        if (reqUser == null) {
            throw new Exception("User not found for the provided JWT");
        }
        Post post = postService.savedPost(postId,reqUser.getId());
        return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
    }

    /**
     * Likes or unlikes a post for a specific user.
     */
    @PutMapping("/posts/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId,
                                                @RequestHeader ("Authorization") String jwt) throws Exception {
        if (jwt == null || !jwt.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        // Extract the JWT token by removing the "Bearer " prefix
        String token = jwt.substring(7).trim();
        User reqUser = userService.findUserByJwt(token);

        // Ensure the user is found with the provided JWT
        if (reqUser == null) {
            throw new Exception("User not found for the provided JWT");
        }

        Post post = postService.likePost(postId, reqUser.getId());
        return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
    }
}
