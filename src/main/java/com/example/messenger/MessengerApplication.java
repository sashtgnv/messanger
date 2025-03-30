package com.example.messenger;

import com.example.messenger.models.User;
import com.example.messenger.services.MessageService;
import com.example.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

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
//        User u1 = userService.findById(1L);
//        User u2 = userService.findById(2L);
//        for (int i = 0; i<10_000;i++) {
//            messageService.update(new Message(
//                    null,
//                    u1,u2, LocalDateTime.now(),String.valueOf(i)
//            ));
//        }
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
