package com.App.Yogesh.Controller;

import com.App.Yogesh.Models.User;
import com.App.Yogesh.Repository.UserRepository;
import com.App.Yogesh.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Retrieves all users.
     */
    @GetMapping("/api/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a specific user by their ID.
     */
    @GetMapping("/api/users/{userId}")
    public Optional<User> getUserById(@PathVariable("userId") Integer id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new Exception("User not found with ID " + id);
        }
        return user;
    }


    /**
     * Updates user information for a specific user ID.
     */
    @PutMapping("/api/users")
    public User updateUser(@RequestHeader("Authorization")String jwt) throws Exception {
        User reqUser = userService.findUserByJwt(jwt);
        User updateUser= userService.findUserByJwt(jwt);
        return updateUser;
    }

    /**
     * Allows one user to follow another user.
     */
    @PutMapping("/api/users/follow/{userId2}")
    public User followUserHandler(@RequestHeader ("Authorization") String jwt,  @PathVariable Integer userId2) throws Exception {
        User reqUser= userService.findUserByJwt(jwt);
        User user = userService.followUser(reqUser.getId(),userId2);
        return user;
    }

    /**
     * Searches for users based on a query string.
     */
    @GetMapping("/api/users/search")
    public List<User> searchUser(@RequestParam("query") String query) {
        List<User> users = userService.searchUser(query);
        return users;
    }

    @GetMapping("/api/users/profile")
    public User getUserFromToken(@RequestHeader("Authorization")String jwt){
        System.out.println("jwt----------"+jwt);
        User user = userService.findUserByJwt(jwt);
        user.setPassword(null);
        return user;
    }

}
