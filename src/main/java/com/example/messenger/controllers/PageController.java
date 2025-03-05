package com.example.messenger.controllers;

import com.example.messenger.models.User;
import com.example.messenger.repositoires.MessageRepository;
import com.example.messenger.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {
    private final UserService userService;
    private final MessageRepository messageRepository;

    public PageController(UserService userService, MessageRepository messageRepository) {
        this.userService = userService;
        this.messageRepository = messageRepository;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("error", true);
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/")
    public String mainPage() {
        return "main-page";
    }

    @GetMapping("/{idRecipient}")
    public String chat() {
        return "chat-page";
    }


}
