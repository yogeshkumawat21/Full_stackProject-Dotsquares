package com.App.Yogesh.ServiceImplmentation;

import com.App.Yogesh.Models.User;
import com.App.Yogesh.Repository.UserRepository;
import com.App.Yogesh.Services.UserService;
import com.App.Yogesh.config.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public User registeruser(User user) {
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setPassword(user.getPassword());
        newUser.setId(user.getId());

        User savedUser = userRepository.save(newUser);
        return savedUser;
    }

    @Override
    public User findUserById(Integer id) throws Exception{
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent())
        {

            return user.get();
        }
        throw new Exception("user not exist with userId"+id);

    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public User followUser(Integer reqUserId, Integer userId2) throws Exception {
        User reqUser = findUserById(reqUserId);
        User user2 = findUserById(userId2);

        // Add the entire user object instead of just the ID
        user2.getFollowers().add(reqUser.getId());  // Assuming followers is a List<User>
        reqUser.getFollowings().add(user2.getId()); // Assuming followings is a List<User>

        // Save the updated users
        userRepository.save(reqUser);
        userRepository.save(user2);

        return reqUser;  // Return the updated user1 with new followings
    }


    @Override
    public User updateUser(User user,@PathVariable Integer userId) throws Exception {
        Optional<User> user1 = userRepository.findById(userId);
        if(user1.isEmpty())
        {
            throw new Exception("user not exist with user Id"+userId);
        }

        User oldUser = user1.get();
        if(user.getFirstName()!=null)
        {
            oldUser.setFirstName(user.getFirstName());
        }
        if(user.getLastName()!=null)
        {
            oldUser.setLastName(user.getLastName());
        }
        if(user.getEmail()!=null)
        {
            oldUser.setEmail(user.getEmail());
        }
        if(user.getGender()!=null)
        {
            oldUser.setGender(user.getGender());
        }
        User updatedUser = userRepository.save(oldUser
        );

        return  oldUser;
    }

    @Override
    public List<User> searchUser(String query) {
       return userRepository.searchUser(query);
    }

    @Override
    public User findUserByJwt(String jwt) {
        String email = JwtProvider.getEmailFromJwtToken(jwt);
        User user = userRepository.findByEmail(email);
        return user;

    }
}
