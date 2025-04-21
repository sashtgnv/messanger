package com.example.messenger;

import com.example.messenger.enums.Role;
import com.example.messenger.models.Message;
import com.example.messenger.models.User;
import com.example.messenger.repositoires.MessageRepository;
import com.example.messenger.services.MessageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MessageServiceTest {
    @Autowired
    private ApplicationContext context;

    @Mock
    MessageRepository messageRepository;

    @Spy
    @InjectMocks
    MessageService messageService;

    @Test
    void testFindMessageBySenderAndRecipient(){

        /*given*/
        var user1 = new User(1L,"user1","email1","password",true, Set.of(Role.ROLE_USER), LocalDateTime.now(),List.of(),List.of());
        var user2 = new User(2L,"user2","email2","password",true, Set.of(Role.ROLE_USER), LocalDateTime.now(),List.of(),List.of());
        var messageList = List.of(new Message(1L,user1,user2,LocalDateTime.now(),"message1"),
                new Message(2L,user1,user2,LocalDateTime.now(),"message2"));

        Mockito.when(messageRepository.findBySenderAndRecipient(user1.getId(), user2.getId())).thenReturn(messageList);

        /*when*/

        var result = messageService.findBySenderAndRecipient(user1.getId(), user2.getId());

        /*then*/

        assertEquals(result,messageList);

    }

    @Test
    void testFindLastMessageBySenderAndRecipient() {

        /*given*/
        var user1 = new User(1L,"user1","email1","password",true, Set.of(Role.ROLE_USER), LocalDateTime.now(),List.of(),List.of());
        var user2 = new User(2L,"user2","email2","password",true, Set.of(Role.ROLE_USER), LocalDateTime.now(),List.of(),List.of());
        var message3 = new Message(3L,user1,user2,LocalDateTime.now(),"message3");

        Mockito.when(messageRepository.findLastBySenderAndRecipient(user1.getId(), user2.getId())).thenReturn(message3);

        /*when*/

        var result1 = messageService.findLastBySenderAndRecipient(user1.getId(), user2.getId());
        var result2 = messageService.findLastBySenderAndRecipient(user1.getId(), user2.getId());

        /*then*/

        assertEquals(result1,message3);
        assertEquals(result2,message3);

        context.getBean("cacheManager");

//        Mockito.verify(messageRepository,Mockito.times(1)).findLastBySenderAndRecipient(user1.getId(), user2.getId());

    }


}
