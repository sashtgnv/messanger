package com.example.messenger.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_sender")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "id_recipient")
    private User recipient;
    private LocalDateTime sendTime;
    @Column(columnDefinition = "text")
    private String messageText;
    
}
