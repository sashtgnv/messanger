package com.example.messenger.repositoires;

import com.example.messenger.models.Message;
import com.example.messenger.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @NativeQuery(value = "select * from messages where id_sender = :user1 and id_recipient = :user2 or id_sender = :user2 and id_recipient = :user1;")
    List<Message> findBySenderAndRecipient(Long user1, Long user2);
}
