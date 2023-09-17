package com.example.botsender.telegram.service.message;

import com.example.botsender.telegram.exceptions.UserNotFoundException;
import com.example.botsender.telegram.service.TelegramBot;
import com.example.botsender.telegram.service.UserService;
import com.example.botsender.webapp.dto.FileInfo;
import jakarta.activation.MimetypesFileTypeMap;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.List;

@Service
public class MediaSender {
    private final TelegramBot telegramBot;
    private final UserService userService;

    public MediaSender(TelegramBot telegramBot, UserService userService) {
        this.telegramBot = telegramBot;
        this.userService = userService;
    }

    public void sendPhoto(FileInfo fileInfo, File file) throws UserNotFoundException, TelegramApiException {
        List<Long> chatIds = userService.findChatId(fileInfo);
        String contentMediaType = getContentMediaType(file);
        InputFile inputFile = new InputFile(file);
        if (contentMediaType.equals("image/jpeg")) {
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setPhoto(inputFile);
            for (Long chatId : chatIds) {
                sendPhoto.setChatId(chatId);
                telegramBot.execute(sendPhoto);
            }
        }
    }

    public void sendDocument(FileInfo fileInfo, File file) throws TelegramApiException, UserNotFoundException {
        List<Long> chatIds = userService.findChatId(fileInfo);
        String contentMediaType = getContentMediaType(file);
        InputFile inputFile = new InputFile(file);
        if (!contentMediaType.equals("image/jpeg")) {
            SendDocument sendDocument = new SendDocument();
            sendDocument.setDocument(inputFile);
            for (Long chatId : chatIds) {
                sendDocument.setChatId(chatId);
                telegramBot.execute(sendDocument);
            }
        }
    }


    private String getContentMediaType(File file) {
        MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
        return mimetypesFileTypeMap.getContentType(file);

    }
}
