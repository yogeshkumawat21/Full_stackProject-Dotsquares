package com.App.Yogesh.Services;
import com.App.Yogesh.Models.User;

import java.util.List;

public interface UserService {

    public User registeruser(User user);

    public User findUserById(Integer userId) throws Exception;

    public User findUserByEmail(String Email);

    public User followUser(Integer userId1, Integer userId2) throws Exception;

    public User updateUser(User user,Integer userId) throws Exception;

    public List<User> searchUser(String query);

    public User findUserByJwt(String jwt);
}
