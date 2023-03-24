package com.example.shoutboxapp.messageapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Map<String, String>> mainPage() {
        String apiPath = "/api/v1/";
        List<Map<String, String>> result = new ArrayList<>();

        result.add(Map.of(
                "path", apiPath,
                "method", "GET",
                "description", "Read me path"
        ));

        result.add(Map.of(
                "path", String.format("%smessages", apiPath),
                "method", "GET",
                "description", "get list of all messages"
        ));

        result.add(Map.of(
                "path", String.format("%smessages/{messageId}", apiPath),
                "method", "GET",
                "description", "get message by Id"
        ));

        result.add(Map.of(
                "path", String.format("%smessages", apiPath),
                "method", "POST",
                "description", "Add message"
        ));

        result.add(Map.of(
                "path", String.format("%smessages/{messageId}", apiPath),
                "method", "DELETE",
                "description", "delete message by id"
        ));

        result.add(Map.of(
                "path", String.format("%smessages", apiPath),
                "method", "PUT",
                "description", "override/update message"
        ));

        return result;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll().stream()
                .sorted((message1, message2) -> message2.getPublishedTime().compareTo(message1.getPublishedTime()))
                .toList();
    }

    public void addMessage(Message message) {
        if (message.getAuthor().isEmpty() || message.getMessage().isEmpty()) {
            return;
        }
        // simple anti-spam filter
        int secondsLimit = 5;
        if (message.getMessage().equals(messageRepository.findAll().get(messageRepository.findAll().size()-1).getMessage())
        && message.getAuthor().equals(messageRepository.findAll().get(messageRepository.findAll().size()-1).getAuthor())) {
            return;
        }
        List<Message> similarMessages = new ArrayList<>();
        messageRepository.findAll().stream()
                .filter(existsMessage -> existsMessage.getAuthor().equals(message.getAuthor()))
                .forEach(similarMessages::add);
        messageRepository.findAll().stream()
                .filter(existsMessage -> existsMessage.getMessage().equals(message.getMessage()))
                .forEach(similarMessages::add);
        for (Message similarMessage: similarMessages) {
            if (ChronoUnit.SECONDS.between(similarMessage.getPublishedTime(), message.getPublishedTime()) < secondsLimit) {
                return;
            }
        }
        messageRepository.save(message);
    }

    public Message getMessageById(String messageId) {
        Long id;
        try {
            id = Long.valueOf(messageId);
        } catch (NumberFormatException e) {
            return new Message(-1L, "Error", String.format("%s is not an integer", messageId), LocalDateTime.now());
        }
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            return message.get();
        } else {
            return new Message(-2L, "Error", String.format("There is no message with ID %s", id), LocalDateTime.now());
        }
    }

    public void deleteMessage(String messageId) {
        Long id;
        try {
            id = Long.valueOf(messageId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format("%s is not integer", messageId));
        }
        messageRepository.deleteById(id);
    }

    public void updateMessage(Message message) {
        if (messageRepository.findById(message.getId()).isPresent()) {
            messageRepository.save(message);
        } else {
            throw new IllegalArgumentException(String.format("Can't update message. ID %s is not exists", message.getId()));
        }
    }
}
