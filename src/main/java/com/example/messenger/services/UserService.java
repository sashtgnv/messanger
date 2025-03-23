package com.example.messenger.services;

import com.example.messenger.enums.Role;
import com.example.messenger.models.User;
import com.example.messenger.repositoires.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createUser(User user) {
        if  (userRepository.findByEmail(user.getEmail())==null && userRepository.findByUsername(user.getUsername())==null){
            user.setActive(true);

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.getRoles().add(Role.ROLE_USER);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findUserFriends(User user){
        return userRepository.findUserFriends(user.getId());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
