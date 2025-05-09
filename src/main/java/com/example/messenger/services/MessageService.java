package com.example.messenger.services;

import com.example.messenger.models.Message;
import com.example.messenger.models.User;
import com.example.messenger.repositoires.MessageRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @CacheEvict(value = "messages", key = "#message.getSender().getId() + '_' + #message.getRecipient().getId()")
    public void save(Message message) {
        messageRepository.save(message);
    }


    public List<Message> findBySenderAndRecipient(Long user1Id, Long user2Id) {
        return messageRepository.findBySenderAndRecipient(user1Id, user2Id);
    }

    @Cacheable(value = "messages", key = "#user1Id + '_' + #user2Id")
    public Message findLastBySenderAndRecipient(Long user1Id, Long user2Id) {
        return messageRepository.findLastBySenderAndRecipient(user1Id,user2Id);
    }
}
