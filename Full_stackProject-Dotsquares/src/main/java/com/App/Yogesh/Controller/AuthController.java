package com.App.Yogesh.Controller;

import com.App.Yogesh.Models.User;
import com.App.Yogesh.Repository.UserRepository;
import com.App.Yogesh.Response.AuthResponse;
import com.App.Yogesh.ServiceImplmentation.CustomUserDetailService;
import com.App.Yogesh.Services.UserService;
import com.App.Yogesh.config.JwtProvider;
import com.App.Yogesh.request.LoginRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Creates a new user or updates an existing user.
     */
    @PostMapping("/signup")
    public AuthResponse createUser(@Valid @RequestBody User user) throws Exception {
        User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            throw new Exception("This email is already associated with another account.");
        }
        if (user.getPassword() == null ||user.getGender()==null||user.getFirstName()==null) {
            throw new IllegalArgumentException("Fill the Mandatory user inputs");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return new AuthResponse("Registration Successful", null);
    }

    /**
     * Authenticates a user and returns a JWT token if successful.
     */
    @PostMapping("/signin")
    public AuthResponse signin(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        String email = authentication.getName();
        List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());

        String token = JwtProvider.generateToken(email, authorities);

        return new AuthResponse("Login Successful", token);
    }

    private Authentication authenticate(String email, String password) {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(email);

        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid email or password.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
