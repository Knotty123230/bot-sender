package com.example.botsender.telegram.service;

import com.example.botsender.telegram.shleduled.ScheduledService;
import com.example.botsender.webapp.dto.FileInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@EnableRabbit
@Service
public class Receiver {
    private final ScheduledService service;

    public Receiver(@Qualifier(value = "scheduler") ScheduledService service) {
        this.service = service;
    }


    @RabbitListener(queues = "fileQueue")
    public void receiveFile(byte[] messageBytes) {
        try {
            String messageString = new String(messageBytes, StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            service.setFileInfo(objectMapper.readValue(messageString, FileInfo.class));
        } catch (IOException e) {
            e.getMessage();
        }
    }

}
