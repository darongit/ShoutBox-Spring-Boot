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
        messageRepository.save(new Message(0L, "Author", "Hello there", LocalDateTime.now()));
        messageRepository.save(new Message(1L, "Somebody", "Send something", LocalDateTime.now()));
    }
}
