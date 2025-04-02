package com.example.messenger;

import com.example.messenger.models.Message;
import com.example.messenger.models.User;
import com.example.messenger.services.MessageService;
import com.example.messenger.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;

@SpringBootTest
class MessengerApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

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

	@Test
	void testFindMessageLastBySenderAndRecipient() {

		Message message1 = messageService.findLastBySenderAndRecipient(1L, 2L);
		System.out.println(message1.getMessageText());

		Message message2 = messageService.findLastBySenderAndRecipient(1L, 2L);
		System.out.println(message2.getMessageText());

		context.getBean("cacheManager");
	}

	@Test
	void testSaveMessage() {

		Message message1 = messageService.findLastBySenderAndRecipient(1L, 2L);
		System.out.println(message1.getMessageText());

		User user = userService.findById(1L);
		User user2 = userService.findById(2L);


		Message newMessage = new Message(null,user,user2, LocalDateTime.now(),"new massage");
		messageService.save(newMessage);
		context.getBean("cacheManager");
//		Message message2 = messageService.findLastBySenderAndRecipient(1L, 2L);
//		System.out.println(message2.getMessageText());

		context.getBean("cacheManager");
	}



}
