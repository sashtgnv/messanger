package com.example.messenger.repositoires;

import com.example.messenger.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    @NativeQuery(value = "select * from users where id in (select distinct(id_sender) from messages m where id_recipient = :user) or id in (select distinct(id_recipient) from messages where id_sender=:user);")
    List<User> findUserFriends(Long user);
}
