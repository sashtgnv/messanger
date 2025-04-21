package com.example.messenger;

import com.example.messenger.enums.Role;
import com.example.messenger.models.User;
import com.example.messenger.services.MessageService;
import com.example.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class MessengerApplication {
    static UserService userService;
    static MessageService messageService;
    static PasswordEncoder passwordEncoder;

    @Autowired
    public MessengerApplication(UserService userService, MessageService messageService, PasswordEncoder passwordEncoder) {
        MessengerApplication.userService = userService;
        MessengerApplication.messageService = messageService;
        MessengerApplication.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
		SpringApplication.run(MessengerApplication.class, args);
        String p1 = "1234";
        String p2 = "4321";

		userService.createUser(new User(null,
				"testuser1",
				"test@email.com",
				p1,
				true,
				null,
				null));
        userService.createUser(new User(null,
				"testuser2",
				"test2@email.com",
				p2,
				true,
				null,
				null));
	}
}
