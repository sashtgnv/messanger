package com.example.messenger.controllers;

import com.example.messenger.models.User;
import com.example.messenger.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String mainPage(HttpServletRequest request,Model model, @AuthenticationPrincipal User user){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("users",userService.getAllUsers());
        return "main-page";
    }

    @GetMapping("/{idRecipient}")
    public String chat(Model model, HttpServletRequest request, @AuthenticationPrincipal User user, @PathVariable Long idRecipient){
        User recipient = null;
        try {
            recipient = userService.getById(idRecipient);
        } catch (NullPointerException e) {
            return "redirect:/";
        }
        model.addAttribute("recipient", recipient);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("users",userService.getAllUsers());
        return "main-page";
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


}
