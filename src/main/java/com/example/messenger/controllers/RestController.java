package com.example.messenger.controllers;

import com.example.messenger.models.Message;
import com.example.messenger.models.User;
import com.example.messenger.repositoires.MessageRepository;
import com.example.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    private MessageRepository messageRepository;
    private UserService userService;

    public RestController(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @GetMapping("/friends")
    public Map<Long,String> rest(@AuthenticationPrincipal User user){
        Map<Long,String> userMap = new HashMap<>();
        for (User u :userService.findUserFriends(user)) {
            userMap.put(u.getId(), u.getUsername());
        }
        return userMap;
    }

    @GetMapping("/find_user")
    public Map<Long,String> find(@RequestParam String username, @AuthenticationPrincipal User user){
        Map<Long,String> userMap = new HashMap<>();
        if (username.isEmpty()) {
            System.out.println("empty username");
            return null;
        } else {
            System.out.println("find user");
            userMap.put(userService.findByUsername(username).getId(),username);
            return userMap;
        }
    }

    @GetMapping("/{idRecipient}")
    public String chat(@AuthenticationPrincipal User user, @PathVariable Long idRecipient){
//        User recipient = null;
//        try {
//            recipient = userService.findById(idRecipient);
//        } catch (NullPointerException e) {
//            return "redirect:/";
//        }
//        model.addAttribute("recipient", recipient);
//        return "main-page";

        return userService.findById(idRecipient).getUsername();
    }

    @PostMapping("/{idRecipient}")
    public void postMessage(@RequestParam String messageText, Model model, @AuthenticationPrincipal User sender, @PathVariable Long idRecipient){
        if (!messageText.isEmpty()) {
            User recipient = userService.findById(idRecipient);
            Message message = new Message(null,sender,recipient, LocalDateTime.now(),messageText);
            messageRepository.save(message);
        }
    }
}
