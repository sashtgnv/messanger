package com.example.messenger.services;

import com.example.messenger.models.Message;
import com.example.messenger.models.User;
import com.example.messenger.repositoires.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void save(Message message) {
        messageRepository.save(message);
    }

    public List<Message> findBySenderAndRecipient(User user1, User user2) {
        return messageRepository.findBySenderAndRecipient(user1.getId(),user2.getId());
    }
}
