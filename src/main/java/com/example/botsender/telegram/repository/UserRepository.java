package com.example.botsender.telegram.repository;

import com.example.botsender.telegram.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByChatId(Long chatId);
    @Query("select chatId from User")
    List<Long> findAllChatIds();
    User findByUsername(String username);
}
