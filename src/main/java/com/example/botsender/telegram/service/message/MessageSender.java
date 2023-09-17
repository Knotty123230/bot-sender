package com.example.botsender.telegram.service.message;

import com.example.botsender.telegram.exceptions.UserNotFoundException;
import com.example.botsender.telegram.service.TelegramBot;
import com.example.botsender.telegram.service.UserService;
import com.example.botsender.webapp.dto.FileInfo;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
public class MessageSender {
    private final UserService userService;
    private final TelegramBot telegramBot;

    public MessageSender(UserService userService, TelegramBot telegramBot) {
        this.userService = userService;
        this.telegramBot = telegramBot;
    }

    public void sendMessage(FileInfo fileInfo) throws UserNotFoundException, TelegramApiException {

        if (fileInfo.getMessage() != null) {
            SendMessage sendMessage = new SendMessage();
            List<Long> chatId = userService.findChatId(fileInfo);
            if (!chatId.isEmpty()) {
                for (Long l : chatId) {
                sendMessage.setChatId(l);
                sendMessage.setText(fileInfo.getMessage());
                telegramBot.execute(sendMessage);
                }
            }


        }
    }
}
