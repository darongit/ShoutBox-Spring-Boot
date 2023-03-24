package com.example.shoutboxapp.messageapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class MessageConfig implements CommandLineRunner {

    private MessageRepository messageRepository;

    @Autowired
    public MessageConfig(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Message toAdd = new Message("Author", "Hello there");
        toAdd.setPublishedTime(LocalDateTime.now().minusMinutes(5L));
        messageRepository.save(toAdd);

        toAdd = new Message("No name", "Example message");
        toAdd.setPublishedTime(LocalDateTime.now().minusMinutes(1L));
        messageRepository.save(toAdd);

        toAdd = new Message("Somebody", "Send something");
        toAdd.setPublishedTime(LocalDateTime.now().minusSeconds(30L));
        messageRepository.save(toAdd);

        toAdd = new Message("Bill", "First message");
        toAdd.setPublishedTime(LocalDateTime.now().minusDays(2L));
        messageRepository.save(toAdd);

        toAdd = new Message("Start", "You are welcome");
        toAdd.setPublishedTime(LocalDateTime.now());
        messageRepository.save(toAdd);
    }
}
