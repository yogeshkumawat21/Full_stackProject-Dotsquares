/**
 * Represents a user in the application with personal details such as first name, last name,
 * email, password, a list of followers and followings, and posts saved by the user.
 */


package com.App.Yogesh.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String firstName;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String gender;
    private String lastName;
    private String email;
    private List<Integer> followings=new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    private List<Post> savedPost= new ArrayList<>();


    public User(){};
    private String password ;
    private List<Integer> followers=new ArrayList<>();

    public List<Post> getSavedPost() {
        return savedPost;
    }

    public void setSavedPost(List<Post> savedPost) {
        this.savedPost = savedPost;
    }



    public User(String firstName, int id, String lastName, String email, String password,String gender, List<Integer> followers, List<Integer> followings, List<Post> savedPost) {
        super();
        this.firstName = firstName;
        this.gender=gender;
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.followers = followers;
        this.followings = followings;
        this.savedPost = savedPost;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", gender='" + gender + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", followings=" + followings +
                ", savedPost=" + savedPost +
                ", password='" + password + '\'' +
                ", followers=" + followers +
                '}';
    }
}
