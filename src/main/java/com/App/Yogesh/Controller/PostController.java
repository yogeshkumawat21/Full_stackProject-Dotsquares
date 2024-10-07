package com.App.Yogesh.Controller;

import com.App.Yogesh.Models.Post;
import com.App.Yogesh.Models.User;
import com.App.Yogesh.Response.ApiResponse;
import com.App.Yogesh.Services.PostService;
import com.App.Yogesh.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Post> createPost
    (@RequestHeader("Authorization") String jwt, @RequestBody Post post) throws Exception {

        User reqUser = userService.findUserByJwt(jwt);
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
        User reqUser = userService.findUserByJwt(jwt);
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
        User reqUser = userService.findUserByJwt(jwt);
        Post post = postService.savedPost(postId,reqUser.getId());
        return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
    }

    /**
     * Likes or unlikes a post for a specific user.
     */
    @PutMapping("/posts/like/{postId}")
    public ResponseEntity<Post> likePostHandler(@PathVariable Integer postId,
                                                @RequestHeader ("Authorization") String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        Post post = postService.likePost(postId, reqUser.getId());
        return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
    }
}
