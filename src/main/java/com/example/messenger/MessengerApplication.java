package com.example.messenger;

import com.example.messenger.enums.Role;
import com.example.messenger.models.Message;
import com.example.messenger.models.User;
import com.example.messenger.services.MessageService;
import com.example.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class MessengerApplication {

	static UserService userService;
    static MessageService messageService;

	@Autowired
    public MessengerApplication(UserService userService, MessageService messageService) {
        MessengerApplication.userService = userService;
        MessengerApplication.messageService = messageService;
    }


    public static void main(String[] args) {
		SpringApplication.run(MessengerApplication.class, args);
        /*List<Message> messages = messageService.findBySenderAndRecipient(1L, 2L);
        System.out.println((messages.getLast().getId()==1) ? List.of() : messages);*/

		/*userService.createUser(new User(null,
				"sashtgnv",
				"sashtgnv@email.com",
				"$2a$12$VTM3U9HPDtNAaGNk5Vt8J.qESFqUuwjOcCynzLv6BRbX9EPXhk/9K",
				true,
				Set.of(Role.ROLE_USER,Role.ROLE_ADMIN),
				null));*/
	}
}
