package com.example.botsender.webapp.service;

import com.example.botsender.telegram.dto.User;
import com.example.botsender.webapp.dto.FileInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

import static com.example.botsender.webapp.constants.FileConstants.FILEPATH;

@Component
public class JsonService {
    private final RabbitTemplate rabbitTemplate;
    private final Queue fileQueue;

    public JsonService(RabbitTemplate rabbitTemplate, Queue fileQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.fileQueue = fileQueue;
    }

    public void objectToStringJson(MultipartFile file, User user) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String fileInfoJson;
        if (user.getUsername() != null) {
            fileInfoJson = objectMapper.writeValueAsString(
                    new FileInfo(user.getMessage(), file.getOriginalFilename(), FILEPATH
                            + file.getOriginalFilename(), Set.of(user.getUsername()))

            );
        }else {
            fileInfoJson = objectMapper.writeValueAsString(
                    new FileInfo(user.getMessage(), file.getOriginalFilename(), FILEPATH
                            + file.getOriginalFilename()));



        }
        rabbitTemplate.convertAndSend(fileQueue.getName(), fileInfoJson);
    }
}
