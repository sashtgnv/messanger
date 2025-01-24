package com.example.messenger.services;

import com.example.messenger.models.User;
import com.example.messenger.repositoires.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        if  (userRepository.findByEmail(user.getEmail())==null){
            userRepository.save(user);
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
