package com.App.Yogesh.Dto;

import com.App.Yogesh.Models.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private List<Integer> followers;
    private List<Integer> followings;

    // Default constructor
    public UserDto() {
        this.followers = new ArrayList<>();
        this.followings = new ArrayList<>();
    }

    // Constructor that takes a User object
    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.gender = user.getGender();
        this.email = user.getEmail();
        this.followers = user.getFollowers();
        this.followings = user.getFollowings();
    }

    // Optional constructor
    public UserDto(Optional<User> user) {
        user.ifPresent(u -> {
            this.id = u.getId();
            this.firstName = u.getFirstName();
            this.lastName = u.getLastName();
            this.gender = u.getGender();
            this.email = u.getEmail();
            this.followers = u.getFollowers();
            this.followings = u.getFollowings();
        });
    }
    private List<GrantedAuthority> authorities; // Add this field

    // existing constructors...

    public List<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Integer> followers) {
        this.followers = followers;
    }

    public List<Integer> getFollowings() {
        return followings;
    }

    public void setFollowings(List<Integer> followings) {
        this.followings = followings;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", followers=" + followers +
                ", followings=" + followings +
                '}';
    }
}
