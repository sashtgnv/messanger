package com.example.messenger.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sender")
    private User sender;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recipient")
    private User recipient;
    private LocalDateTime sendTime;
    @Column(columnDefinition = "text")
    private String messageText;

    public Message() {
    }

    public Message(Long id, User sender, User recipient, LocalDateTime sendTime, String messageText) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.sendTime = sendTime;
        this.messageText = messageText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(sender, message.sender) && Objects.equals(recipient, message.recipient) && Objects.equals(sendTime, message.sendTime) && Objects.equals(messageText, message.messageText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, recipient, sendTime, messageText);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", sender=" + sender.getUsername() +
                ", recipient=" + recipient.getUsername() +
                ", sendTime=" + sendTime +
                ", messageText='" + messageText + '\'' +
                '}';
    }

    public MessageDTO getDTO(){
        return new MessageDTO(getId(),getSender().getDTO(),getRecipient().getDTO(),getSendTime(),getMessageText());
    }

    public class MessageDTO{
        private Long id;
        private User.UserDTO sender;
        private User.UserDTO recipient;
        private LocalDateTime sendTime;
        private String messageText;

        public MessageDTO(Long id, User.UserDTO sender, User.UserDTO recipient, LocalDateTime sendTime, String messageText) {
            this.id = id;
            this.sender = sender;
            this.recipient = recipient;
            this.sendTime = sendTime;
            this.messageText = messageText;
        }

        public Long getId() {
            return id;
        }

        public User.UserDTO getSender() {
            return sender;
        }

        public User.UserDTO getRecipient() {
            return recipient;
        }

        public LocalDateTime getSendTime() {
            return sendTime;
        }

        public String getMessageText() {
            return messageText;
        }
    }
}
