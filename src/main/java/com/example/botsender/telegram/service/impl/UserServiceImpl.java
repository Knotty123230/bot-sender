package com.example.botsender.telegram.service.impl;

import com.example.botsender.telegram.dto.User;
import com.example.botsender.telegram.exceptions.UserNotFoundException;
import com.example.botsender.telegram.repository.UserRepository;
import com.example.botsender.telegram.service.UserService;
import com.example.botsender.webapp.dto.FileInfo;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;

@Component("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveUser(Update update) {
        if (repository.findByChatId(update.getMessage().getChatId()) == null){
            User user = new User();
            user.setChatId(update.getMessage().getChatId());
            user.setName(update.getMessage().getFrom().getFirstName());
            user.setUsername(update.getMessage().getFrom().getUserName());
            repository.save(user);
        }
    }

    @Override
    public User findUserByChatId(Long chatId) {
        return repository.findByChatId(chatId);
    }

    @Override
    public List<Long> findAllChatIds() {
        return repository.findAllChatIds();
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
         if (repository.findByUsername(username) == null){
             throw new UserNotFoundException(String.format("User not found %s", username));
         }
         return repository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }
    public List<Long> findChatId(FileInfo fileInfo) throws UserNotFoundException {
        Optional<String> first = fileInfo.getUsernames().stream().findFirst();
        if (first.isPresent() && !first.get().isEmpty()) {
            User userByChatId = findByUsername(first.get());
            return List.of(userByChatId.getChatId());
        }
        return findAllChatIds();
    }
}
