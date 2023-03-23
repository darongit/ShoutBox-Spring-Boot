package com.example.shoutboxapp.messageapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class MessageController {
    private MessageService messageService;
    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/")
    public List<Map<String, String>> mainPage() {
        return messageService.mainPage();
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{messageId}")
    public Message getMessageById(@PathVariable String messageId) {
        return messageService.getMessageById(messageId);
    }

    @PostMapping("/messages")
    public void addMessage(@RequestBody Message message) {
        messageService.addMessage(message);
    }

    @DeleteMapping("/messages/{messageId}")
    public void deleteMessage(@PathVariable String messageId) {
        messageService.deleteMessage(messageId);
    }

    @PutMapping("/messages")
    public void updateMessage(@RequestBody Message message) {
        messageService.updateMessage(message);
    }

}
