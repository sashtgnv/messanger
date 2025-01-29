package com.example.messenger.models;

import com.example.messenger.enums.Role;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    @Column(length = 1000)
    private String password;
    private boolean active;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    private LocalDateTime dateOfCreated;
    @OneToMany(mappedBy = "sender",fetch = FetchType.LAZY)
    private List<Message> sendedMessages;
    @OneToMany(mappedBy = "recipient",fetch = FetchType.LAZY)
    private List<Message> recipientedMesseges;

    public User() {
    }

    public User(Long id, String username, String email, String password, boolean active, Set<Role> roles, LocalDateTime dateOfCreated, List<Message> sendedMessages, List<Message> recipientedMesseges) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.active = active;
        this.roles = roles;
        this.dateOfCreated = dateOfCreated;
        this.sendedMessages = sendedMessages;
        this.recipientedMesseges = recipientedMesseges;
    }

    @PrePersist
    private void init(){
        dateOfCreated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getDateOfCreated() {
        return dateOfCreated;
    }

    public void setDateOfCreated(LocalDateTime dateOfCreated) {
        this.dateOfCreated = dateOfCreated;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Message> getSendedMessages() {
        return sendedMessages;
    }

    public void setSendedMessages(List<Message> sendedMessages) {
        this.sendedMessages = sendedMessages;
    }

    public List<Message> getRecipientedMesseges() {
        return recipientedMesseges;
    }

    public void setRecipientedMesseges(List<Message> recipientedMesseges) {
        this.recipientedMesseges = recipientedMesseges;
    }

    /*security*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }


}
