package com.App.Yogesh.ServiceImplmentation;

import com.App.Yogesh.Models.Post;
import com.App.Yogesh.Models.User;
import com.App.Yogesh.Repository.PostRepository;
import com.App.Yogesh.Repository.UserRepository;
import com.App.Yogesh.Services.PostService;
import com.App.Yogesh.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImplementation implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public Post createNewPost(Post post, Integer userId) throws Exception {
        // Find the user who is creating the post
        User user = userService.findUserById(userId);

        // Set up the new post
        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setImage(post.getImage());
        newPost.setVideo(post.getVideo());
        newPost.setCreatedAt(LocalDateTime.now()); // Set the creation time
        newPost.setUser(user);

        // Save the new post to the repository
        return postRepository.save(newPost);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws Exception {
        // Find the post and user
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        // Check if the user owns the post
        if (post.getUser().getId() != user.getId()) { // Use == instead of equals()
            throw new Exception("You can't delete another user's post.");
        }

        // Delete the post
        postRepository.delete(post);
        return "Post deleted successfully";
    }


    @Override
    public List<Post> findPostByUserId(Integer userId) {
        return postRepository.findPostByUserId(userId);
    }

    @Override
    public Post findPostById(Integer postId) {
        Optional<Post> opt = postRepository.findById(postId);
        return opt.orElse(null); // Return the post if found, otherwise return null
    }


    @Override
    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

    @Override
    public Post savedPost(Integer postId, Integer userId) throws Exception {
        // Find the post and user
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        // Toggle the saved status of the post
        if (user.getSavedPost().contains(post)) {
            user.getSavedPost().remove(post);
        } else {
            user.getSavedPost().add(post);
        }

        // Save the updated user
        userRepository.save(user);
        return post;
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws Exception {
        // Find the post and user
        Post post = findPostById(postId);
        User user = userService.findUserById(userId);

        // Toggle the like status of the post
        if (post.getLiked().contains(user)) {
            post.getLiked().remove(user);
        } else {
            post.getLiked().add(user);
        }

        // Save the updated post
        return postRepository.save(post);
    }
}
