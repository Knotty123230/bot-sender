package com.example.botsender.telegram.service;

import com.example.botsender.telegram.dto.User;
import com.example.botsender.telegram.exceptions.UserNotFoundException;
import com.example.botsender.webapp.dto.FileInfo;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
@Service
public interface UserService {
     void saveUser(Update update);
     User findUserByChatId(Long chatId);
    List<Long> findAllChatIds();
    User findByUsername(String username) throws UserNotFoundException;
    List<User> findAll();
    List<Long> findChatId(FileInfo fileInfo) throws UserNotFoundException;

}
