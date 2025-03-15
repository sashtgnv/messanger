package com.example.messenger.controllers;

import com.example.messenger.models.Message;
import com.example.messenger.models.User;
import com.example.messenger.services.MessageService;
import com.example.messenger.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    /*друзья пользователя*/
    @GetMapping("/friends")
    public List<User.UserDTO> friends(@AuthenticationPrincipal User user,
                                      @RequestHeader("api") boolean api) {
        List<User.UserDTO> userDTOList = new ArrayList<>();
        for (User u : userService.findUserFriends(user)) {
            userDTOList.add(u.getDTO());
        }
        return userDTOList;
    }

    /*поиск пользователя*/
    @GetMapping("/find_user")
    public List<User.UserDTO> find(@RequestParam String username,
                                   @AuthenticationPrincipal User user) {
        List<User.UserDTO> userDTOList = new ArrayList<>();
        if (username.isEmpty()) {
            System.out.println("empty username");
            userDTOList = friends(user, true);
        } else {
            User u = userService.findByUsername(username);
            if (u != null)
                userDTOList.add(u.getDTO());
        }
        return userDTOList;
    }

    /*получение текущего пользователя*/
    @GetMapping("/current_user")
    public User.UserDTO currentUser(@AuthenticationPrincipal User user,
                                    @RequestHeader("api") boolean api) {
        return user.getDTO();
    }

    /*получение текущего собеседника*/
    @GetMapping("/{idRecipient}/getUser")
    public User.UserDTO getUser(@PathVariable Long idRecipient,
                                @RequestHeader("api") boolean api) {
        return userService.findById(idRecipient).getDTO();
    }

    /*получения списка сообщений*/
    @GetMapping("/messages/{idRecipient}")
    public List<Message.MessageDTO> getMessages(@PathVariable Long idRecipient,
                                                @AuthenticationPrincipal User sender,
                                                @RequestParam Long lastMessageid,
                                                @RequestHeader("api") boolean api) {

        List<Message> messages = messageService.findBySenderAndRecipient(sender.getId(), idRecipient);
        List<Message.MessageDTO> messageDTOS = new ArrayList<>();
        for (Message m : messages) messageDTOS.add(m.getDTO());
        return (messages.getLast().getId() == 1L) ? List.of() : messageDTOS;
    }

    /*сохранение сообщения*/
    @PostMapping("/messages/{idRecipient}")
    public boolean postMessage(@RequestParam String messageText,
                               @AuthenticationPrincipal User sender,
                               @PathVariable Long idRecipient) {
        if (!messageText.isEmpty()) {
            User recipient = userService.findById(idRecipient);
            Message message = new Message(null, sender, recipient, LocalDateTime.now(), messageText);
            messageService.save(message);
        }
        return true;
    }


}
