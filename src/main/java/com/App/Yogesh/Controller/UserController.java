//Packages Importing
package com.App.Yogesh.Controller;

import com.App.Yogesh.Dto.UserDetailsDto;
import com.App.Yogesh.Dto.UserDto;
import com.App.Yogesh.Models.User;
import com.App.Yogesh.Repository.UserRepository;
import com.App.Yogesh.Response.ApiResponse;
import com.App.Yogesh.Services.UserService;
import com.App.Yogesh.config.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @Autowired
    private UserContext userContext;


    /**
     * Retrieves all users.
     */
    @GetMapping("/api/users")
    public ResponseEntity<ApiResponse<?>> getUsers() {
        List<User> users = userRepository.findAll(); // Get all users

        if (users.isEmpty()) {
            ApiResponse<UserDto> response = new ApiResponse<>(
                    "No User Found",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        // Map User entities to UserDto
        List<UserDto> userDtos = users.stream()
                .map(UserDto::new) // Using the constructor that takes a User object
                .collect(Collectors.toList());

        ApiResponse<List<UserDto>> response = new ApiResponse<>(
                "Users retrieved successfully", HttpStatus.OK.value(), userDtos
        );
        return ResponseEntity.ok(response); // Return the list of UserDto
    }


    /**
     * Retrieves a specific user by their ID.
     */
    @GetMapping("/api/users/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable("userId") Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            ApiResponse<UserDto> response = new ApiResponse<>(
                    "User Not Found With this Id",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        UserDto userDto = new UserDto(user.get());

        ApiResponse<UserDto> response = new ApiResponse<>(
                "User retrieved successfully",
                HttpStatus.FOUND.value(),
                userDto
        );
        return ResponseEntity.ok(response);
    }



    /**
     * Updates user information for a specific user ID.
     */
    @PutMapping("/api/users")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(
            @RequestBody User user) { // Add userDto as a request body parameter
        UserDetailsDto currentUser = userContext.getCurrentUser();

        try {
            // Find user by Current User
            User reqUser = userService.findUserByEmail(currentUser.getEmail());

            // Ensure the user is found with the provided JWT
            if (reqUser == null ) {
                ApiResponse<UserDto> response = new ApiResponse<>(
                        "Invalid User",
                        HttpStatus.NOT_FOUND.value(),
                        null
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Update the user using the service method
            User updatedUser = userService.updateUser(user, reqUser.getId());

            // Convert updated User entity to UserDto for response
            UserDto updatedUserDto = new UserDto(updatedUser);
            ApiResponse<UserDto> response = new ApiResponse<>(
                    "User Updated successfully",
                    HttpStatus.OK.value(),
                    updatedUserDto // Use the updatedUserDto here
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Handle decoding errors
            // or any other exceptions gracefully
            ApiResponse<UserDto> response = new ApiResponse<>(
                    "Error processing the JWT token: " + e.getMessage(),
                    HttpStatus.BAD_REQUEST.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }


    /**
     * Allows one user to follow another user.
     */
    @PutMapping("/api/users/follow/{userId2}")
    public ResponseEntity<ApiResponse<UserDto>> followUserHandler(  @PathVariable Integer userId2) throws Exception {

        UserDetailsDto currentUser = userContext.getCurrentUser();
        User reqUser = userService.findUserByEmail(currentUser.getEmail());
        // Ensure the user is found with the provided JWT
        if (reqUser == null) {
            ApiResponse<UserDto> response = new ApiResponse<>(
                    "User Not Found with this token",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        // Execute the follow user logic
        User updatedUser = userService.followUser(reqUser.getId(), userId2);
        UserDto userDto = new UserDto(updatedUser);

        ApiResponse<UserDto> response = new ApiResponse<>(
                "User Follow success",
                HttpStatus.FOUND.value(),
                userDto
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

    }


    /**
     * Searches for users based on a query string.
     */
    @GetMapping("/api/users/search")
    public ResponseEntity<ApiResponse<?>> searchUser(@RequestParam("query") String query) {
        List<User> users = userService.searchUser(query);

        // Check if users list is empty
        if (users.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>(
                    "No users found matching the search query",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // Return the not found response
        }

        // Map User entities to UserDto if users are found
        List<UserDto> userDtos = users.stream()
                .map(UserDto::new) // Using the constructor that takes a User object
                .collect(Collectors.toList());

        ApiResponse<List<UserDto>> response = new ApiResponse<>(
                "Users retrieved successfully",
                HttpStatus.OK.value(),
                userDtos
        );
        return ResponseEntity.ok(response); // Return the list of UserDto
    }

//Get users Profile From Login
    @GetMapping("/api/users/profile")
    public ResponseEntity<ApiResponse<?>> getUserFromToken() throws Exception {
        UserDetailsDto currentUser = userContext.getCurrentUser();
        User user = userService.findUserByEmail(currentUser.getEmail());
        if (user == null) {
            ApiResponse<UserDto> response = new ApiResponse<>(
                    "User Not Found with this token",
                    HttpStatus.NOT_FOUND.value(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        // Execute the follow user logic

        UserDto userDto = new UserDto(user);

        ApiResponse<UserDto> response = new ApiResponse<>(
                "User Profile Found",
                HttpStatus.FOUND.value(),
                userDto
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
