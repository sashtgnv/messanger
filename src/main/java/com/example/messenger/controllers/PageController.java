package com.example.messenger.controllers;

import com.example.messenger.models.User;
import com.example.messenger.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {
    private final UserService userService;

    public PageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/")
    public String mainPage() {
        return "main-page";
    }

    @GetMapping("/{idRecipient}")
    public String chat() {
        return "chat-page";
    }

    @GetMapping("/profile")
    public String profile(){
        return "profile";
    }


}
