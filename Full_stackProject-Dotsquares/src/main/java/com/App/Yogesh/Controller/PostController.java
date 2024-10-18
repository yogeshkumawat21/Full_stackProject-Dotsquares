package com.App.Yogesh.Controller;

import com.App.Yogesh.Dto.PostDto;
import com.App.Yogesh.Models.Post;
import com.App.Yogesh.Models.User;
import com.App.Yogesh.Response.ApiResponse;
import com.App.Yogesh.Services.PostService;
import com.App.Yogesh.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<?> createPost(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody Post post) throws Exception {
        String jwt;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7).trim(); // Remove "Bearer " prefix
        } else {
            throw new IllegalArgumentException("Invalid Authorization header");
        }
        User reqUser = userService.findUserByJwt(jwt);
        if (post.getCaption().isEmpty())
        {

            ApiResponse<PostDto> response = new ApiResponse<>(
                    "Post Not Created",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Post createdPost = postService.createNewPost(post, reqUser.getId());
        PostDto postDto = new PostDto(createdPost);
        // Create the response object
        ApiResponse<PostDto> response = new ApiResponse<>(
                "Post Created Successfully",
                HttpStatus.CREATED.value(),
                postDto
        );
        return ResponseEntity.ok(response);
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
        Post post = postService.findPostById(postId);
        if (post == null) {
            ApiResponse<String> response = new ApiResponse<>(
                    "Post not found",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Attempt to delete the post
       else
        {
            String message = postService.deletePost(postId, reqUser.getId());

            // Create the response object
            ApiResponse<String> response = new ApiResponse<>(message, HttpStatus.OK.value(), null);

            return new ResponseEntity<>(response, HttpStatus.OK);}
        }

    /**
     * Retrieves a post by its ID.
     */
    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<?>> findPostByIdHandler(@PathVariable Integer postId) throws Exception {
        Post post = postService.findPostById(postId);
        if (post == null) {
            ApiResponse<String> response = new ApiResponse<>(
                    "Post not found",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        //Found post
        else
        {
            PostDto postDto =new PostDto(post);

            // Create the response object
            ApiResponse<PostDto> response = new ApiResponse<>("Post Found Successfully", HttpStatus.OK.value(), postDto);

            return new ResponseEntity<>(response, HttpStatus.OK);}
    }



    /**
     * Retrieves all posts made by a specific user.
     */
    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<ApiResponse<List<PostDto>>> findUsersPost(@PathVariable Integer userId) {
        List<Post> posts = postService.findPostByUserId(userId);
        List<PostDto> postDtos = new ArrayList<>();

        // Check if the list is empty, not null
        if (posts == null || posts.isEmpty()) {
            ApiResponse<List<PostDto>> response = new ApiResponse<>(
                    "No Posts Found",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Convert each Post to PostDto
        for (Post post : posts) {
            postDtos.add(new PostDto(post)); // Add the converted PostDto to the list
        }

        // Create the response object
        ApiResponse<List<PostDto>> response = new ApiResponse<>(
                "Posts Found Successfully",
                HttpStatus.OK.value(),
                postDtos
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     * Retrieves all posts.
     */
    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<List<PostDto>>> findAllPost() {
        List<Post> posts = postService.findAllPost();
        List<PostDto> postDtos = new ArrayList<>();
        // Check if the list is empty, not null
        if (posts == null || posts.isEmpty()) {
            ApiResponse<List<PostDto>> response = new ApiResponse<>(
                    "No Posts Found",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        for (Post post : posts) {
            postDtos.add(new PostDto(post)); // Convert each Post to PostDto
        }

        // Create the response object
        ApiResponse<List<PostDto>> response = new ApiResponse<>(
                "Posts retrieved successfully",
                HttpStatus.OK.value(),
                postDtos
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * Saves a post for a specific user (e.g., bookmark or favorite).
     */
    @PutMapping("/posts/save/{postId}")
    public ResponseEntity<ApiResponse<PostDto>> savePostHandler(@PathVariable Integer postId,
                                                                @RequestHeader("Authorization") String jwt) throws Exception {
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

        // Save the post for the user
        Post post = postService.savedPost(postId, reqUser.getId());

        // Check if the post was successfully saved
        if (post == null) {
            ApiResponse<PostDto> response = new ApiResponse<>(
                    "Post not found or could not be saved",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Convert the saved post to PostDto
        PostDto postDto = new PostDto(post);

        // Create the response object
        ApiResponse<PostDto> response = new ApiResponse<>(
                "Post saved successfully",
                HttpStatus.ACCEPTED.value(),
                postDto
        );

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /**
     * Likes or unlikes a post for a specific user.
     */
    @PutMapping("/posts/like/{postId}")
    public ResponseEntity<ApiResponse<PostDto>> likePostHandler(@PathVariable Integer postId,
                                                                @RequestHeader("Authorization") String jwt) throws Exception {
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

        // Attempt to like the post
        Post post = postService.likePost(postId, reqUser.getId());

        // Check if the post was found and liked successfully
        if (post == null) {
            ApiResponse<PostDto> response = new ApiResponse<>(
                    "Post not found or could not be liked",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Convert the liked post to PostDto
        PostDto postDto = new PostDto(post);

        // Create the response object
        ApiResponse<PostDto> response = new ApiResponse<>(
                "Post liked successfully",
                HttpStatus.OK.value(),
                postDto
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
