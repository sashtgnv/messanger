package com.example.messenger;

import com.example.messenger.enums.Role;
import com.example.messenger.models.User;
import com.example.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class MessengerApplication {

	static UserService userService;

	@Autowired
    public MessengerApplication(UserService userService) {
        MessengerApplication.userService = userService;
    }


    public static void main(String[] args) {
		SpringApplication.run(MessengerApplication.class, args);
		/*userService.createUser(new User(null,
				"sashtgnv",
				"sashtgnv@email.com",
				"$2a$12$VTM3U9HPDtNAaGNk5Vt8J.qESFqUuwjOcCynzLv6BRbX9EPXhk/9K",
				true,
				Set.of(Role.ROLE_USER,Role.ROLE_ADMIN),
				null));*/
	}
}
