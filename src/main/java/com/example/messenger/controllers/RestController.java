package com.example.messenger.controllers;

import com.example.messenger.models.Message;
import com.example.messenger.models.User;
import com.example.messenger.services.MessageService;
import com.example.messenger.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private UserService userService;
    private MessageService messageService;

    public RestController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/friends")
    public List<User.UserDTO> friends(@AuthenticationPrincipal User user){
        List<User.UserDTO> userDTOList = new ArrayList<>();
        for (User u :userService.findUserFriends(user)) {
            userDTOList.add(u.getDTO());
        }
        return userDTOList;
    }

    @GetMapping("/find_user")
    public List<User.UserDTO> find(@RequestParam String username, @AuthenticationPrincipal User user){
        List<User.UserDTO> userDTOList = new ArrayList<>();
        if (username.isEmpty()) {
            System.out.println("empty username");
            userDTOList = friends(user);
        } else {
            User u = userService.findByUsername(username);
            if (u!=null)
                userDTOList.add(u.getDTO());
        }
        return userDTOList;
    }

    @GetMapping("/current_user")
    public User.UserDTO currentUser(@AuthenticationPrincipal User user){
        return user.getDTO();
    }


    @GetMapping("/{idRecipient}")
    public String chat(@AuthenticationPrincipal User user, @PathVariable Long idRecipient){
        return userService.findById(idRecipient).getUsername();
    }

    @GetMapping("/messages/{idRecipient}")
    public List<Message> getMessages(@PathVariable Long idRecipient, @AuthenticationPrincipal User sender){
        return messageService.findBySenderAndRecipient(sender.getId(), idRecipient);
    }

    @PostMapping("/messages/{idRecipient}")
    public String  postMessage(@RequestParam String messageText, @AuthenticationPrincipal User sender, @PathVariable Long idRecipient){
        if (!messageText.isEmpty()) {
            User recipient = userService.findById(idRecipient);
            Message message = new Message(null,sender,recipient, LocalDateTime.now(),messageText);
            messageService.save(message);
        }
        return "success";
    }


}
