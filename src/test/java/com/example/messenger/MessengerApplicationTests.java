package com.example.messenger;

import com.example.messenger.models.User;
import com.example.messenger.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class MessengerApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private UserService userService;

	@Test
	void testFindUserById() {
		User user = userService.findById(1L);
		System.out.println(user.getUsername());

		User user2 = userService.findById(1L);
		System.out.println(user2.getUsername());

		context.getBean("cacheManager");
	}

	@Test
	void testFindUserByUsername() {
		User user = userService.findByUsername("sashtgnv");
		System.out.println(user.getUsername());

		User user2 = userService.findByUsername("sashtgnv");
		System.out.println(user2.getUsername());

		context.getBean("cacheManager");
	}

	@Test
	void testUpdateUser() {
		User user = userService.findById(1L);
		System.out.println(user.getUsername());

		User user2 = userService.findById(1L);
		System.out.println(user2.getUsername());

		System.out.println("------------------------------");

		userService.update(user);

		User user3 = userService.findById(1L);
		System.out.println(user3.getUsername());

		context.getBean("cacheManager");
	}

}
