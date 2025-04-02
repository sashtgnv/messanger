package com.example.messenger.services;

import com.example.messenger.enums.Role;
import com.example.messenger.models.User;
import com.example.messenger.repositoires.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ApplicationContext context;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ApplicationContext context) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.context = context;
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

    @CacheEvict(value = "users", key = "#user.id")
    public void update(User user) {
        userRepository.save(user);
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @Cacheable(value = "users", key = "#id")
    public User findById(Long id){
//        context.getBean("cacheManager");
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findUserFriends(User user){
        return userRepository.findUserFriends(user.getId());
    }

    @Cacheable(value = "users", key = "#username")
    public User findByUsername(String username) {
//        context.getBean("cacheManager");
        return userRepository.findByUsername(username);
    }
}
