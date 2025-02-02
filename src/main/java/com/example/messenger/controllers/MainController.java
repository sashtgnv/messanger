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
public class MainController {
    private final UserService userService;
    private final MessageRepository messageRepository;

    @Autowired
    public MainController(UserService userService, MessageRepository messageRepository) {
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
        model.addAttribute("users",userService.findUserFriends(user));
        return "main-page";
    }

    @GetMapping("/{idRecipient}")
    public String chat(Model model, @AuthenticationPrincipal User user, @PathVariable Long idRecipient){
        User recipient = null;
        try {
            recipient = userService.findById(idRecipient);
        } catch (NullPointerException e) {
            return "redirect:/";
        }
        model.addAttribute("recipient", recipient);
        model.addAttribute("sender", user.getUsername());
        model.addAttribute("users",userService.findUserFriends(user));
        return "main-page";
    }

    @PostMapping("/{idRecipient}")
    public String postMessage(@RequestParam String messageText, Model model, @AuthenticationPrincipal User sender, @PathVariable Long idRecipient){
        if (!messageText.isEmpty()) {
            User recipient = userService.findById(idRecipient);
            Message message = new Message(null,sender,recipient, LocalDateTime.now(),messageText);
            messageRepository.save(message);
        }
        return "redirect:/{idRecipient}";
    }

    @GetMapping("/find_user")
    public String find(Model model,@RequestParam String username, @AuthenticationPrincipal User user){
        if (username.isEmpty()) {
            System.out.println(222222222);
            return "redirect:/";
        }
        else {
            System.out.println(11111111);
            model.addAttribute("users", userService.findByUsername(username));
            model.addAttribute("sender", user.getUsername());
            return "main-page";
        }
    }

    /*@GetMapping("/find_user")
    public String find(Model model){
        String username = "bobb";
        if (username.isEmpty()) {
            System.out.println(222222222);
            return "redirect:/";
        }
        else {
            System.out.println(11111111);
            model.addAttribute("users", userService.findByUsername(username));
            return "main-page";
        }
    }*/


}
