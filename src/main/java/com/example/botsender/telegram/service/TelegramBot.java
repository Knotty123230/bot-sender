package com.example.botsender.telegram.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


@Service
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {


    private final UserService userService;
    @Value("${bot.username}")
    private String username;


    public TelegramBot(@Value("${bot.token}") String botToken, @Qualifier("userService") UserService userService) {
        super(botToken);
        this.userService = userService;

    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        userService.saveUser(update);
    }


    @Override
    public String getBotUsername() {
        return username;
    }


}

