package com.example.messenger.controllers;

import com.example.messenger.models.ChangePasswordRequest;
import com.example.messenger.models.Message;
import com.example.messenger.models.User;
import com.example.messenger.services.MessageService;
import com.example.messenger.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    private UserService userService;
    private MessageService messageService;
    private PasswordEncoder passwordEncoder;

    public RestController(UserService userService, MessageService messageService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.messageService = messageService;
        this.passwordEncoder = passwordEncoder;
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
    @GetMapping("/chat/{idRecipient}/getUser")
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
        try {
            return (messages.getLast().getId().equals(lastMessageid)) ? List.of() : messageDTOS;
        } catch (Exception e) {
            return List.of();
        }
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public class BadRequestException extends RuntimeException {
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute User user,
                             HttpServletResponse response) {
        System.out.println(user);
        if (!userService.createUser(user)) {
            return "Ошибка";
        }
        return "Успех";
    }

    @PostMapping("/change_password")
    public String changePassword(@ModelAttribute ChangePasswordRequest changePasswordRequest) throws Exception {
        User user = userService.findByUsername(changePasswordRequest.getUsername());

        if (user!=null && passwordEncoder.matches(changePasswordRequest.getOldPassword(),user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userService.update(user);
            return "Пароль сменен успешно";
        } else {
            return "Неверный логин или пароль";
        }
    }


}
