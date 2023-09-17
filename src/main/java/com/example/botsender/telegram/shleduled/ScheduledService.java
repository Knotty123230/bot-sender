package com.example.botsender.telegram.shleduled;

import com.example.botsender.telegram.exceptions.UserNotFoundException;
import com.example.botsender.telegram.service.message.MediaSender;
import com.example.botsender.telegram.service.message.MessageSender;
import com.example.botsender.webapp.dto.FileInfo;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

@Service(value = "scheduler")
@EnableRabbit
@EnableScheduling
public class ScheduledService {
    private FileInfo fileInfo;
   private final MessageSender messageSender;
   private final MediaSender mediaSender;

    public ScheduledService(MessageSender messageSender, MediaSender mediaSender) {
        this.messageSender = messageSender;
        this.mediaSender = mediaSender;
    }

    @Scheduled(cron = "*/1 * * * * *")
    public void sendScheduledPhotosAutomatically() throws TelegramApiException, UserNotFoundException {
        if (fileInfo != null) {
            sendScheduledPhotos(fileInfo);
            fileInfo = null;
        }
    }

    private void sendScheduledPhotos(FileInfo fileInfo) throws TelegramApiException, UserNotFoundException {
        String path = fileInfo.getPath();
        File file = new File(path);
        messageSender.sendMessage(fileInfo);
        mediaSender.sendPhoto(fileInfo, file);
        mediaSender.sendDocument(fileInfo, file);
    }




    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }
}
