package com.example.botsender.telegram.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usr")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Column(name = "chat_id")
    private Long chatId;
    @Getter
    private String name;
    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private String username;

    public User(Long chatId, String name, String username, String message) {
        this.chatId = chatId;
        this.name = name;
        this.username = username;
        this.message = message;
    }

    public User() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }
}
