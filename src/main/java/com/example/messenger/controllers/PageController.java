package com.example.messenger.controllers;

import com.example.messenger.models.Message;
import com.example.messenger.models.User;
import com.example.messenger.repositoires.MessageRepository;
import com.example.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class PageController {
    private final UserService userService;
    private final MessageRepository messageRepository;

    @Autowired
    public PageController(UserService userService, MessageRepository messageRepository) {
        this.userService = userService;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("error",true);
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/")
    public String mainPage(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("sender", user.getUsername());
        return "main-page";
    }







}
