package com.example.messenger.controllers;

import com.example.messenger.models.Message;
import com.example.messenger.models.User;
import com.example.messenger.services.MessageService;
import com.example.messenger.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private UserService userService;
    private MessageService messageService;

    public RestController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @PostMapping("/friends")
    public List<User.UserDTO> friends(@AuthenticationPrincipal User user){
        List<User.UserDTO> userDTOList = new ArrayList<>();
        for (User u :userService.findUserFriends(user)) {
            userDTOList.add(u.getDTO());
        }
        return userDTOList;
    }

    @PostMapping("/find_user")
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

    @PostMapping("/current_user")
    public User.UserDTO currentUser(@AuthenticationPrincipal User user){
        return user.getDTO();
    }


    @PostMapping("/{idRecipient}/getUser")
    public User.UserDTO getUser(@PathVariable Long idRecipient){
        return userService.findById(idRecipient).getDTO();
    }

    @PostMapping("/messages/{idRecipient}")
    public List<Message.MessageDTO> getMessages(@PathVariable Long idRecipient, @AuthenticationPrincipal User sender, @RequestBody Map<String,Long> json){
        Long lastMessageid = json.get("lastMessageid");
//        System.out.println(lastMessageid);
        List<Message> messages = messageService.findBySenderAndRecipient(sender.getId(), idRecipient);
        List<Message.MessageDTO> messageDTOS = new ArrayList<>();
        for (Message m : messages) messageDTOS.add(m.getDTO());
        return (messages.getLast().getId() == lastMessageid) ? List.of() : messageDTOS;
    }

    @PostMapping("/messages/post/{idRecipient}")
    public String  postMessage(@RequestParam String messageText, @AuthenticationPrincipal User sender, @PathVariable Long idRecipient){
        if (!messageText.isEmpty()) {
            User recipient = userService.findById(idRecipient);
            Message message = new Message(null,sender,recipient, LocalDateTime.now(),messageText);
            messageService.save(message);
//            System.out.println("success save");
        }

        return "success";
    }


}
