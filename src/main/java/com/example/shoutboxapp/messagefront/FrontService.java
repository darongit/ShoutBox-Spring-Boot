package com.example.shoutboxapp.messagefront;

import com.example.shoutboxapp.messageapi.Message;
import com.example.shoutboxapp.messageapi.MessageService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FrontService {

    @Getter
    @AllArgsConstructor
    private class ApiPojo {
        private String path;
        private String method;
        private String description;
    }

    private MessageService messageService;
    @Autowired
    public FrontService(MessageService messageService) {
        this.messageService = messageService;
    }

    public String mainPage(Model model, String author, String message) {
        if (!(author.equals("") && message.equals(""))) {
            messageService.addMessage(new Message(author, message));
        }
        model.addAttribute("messages" ,messageService.getAllMessages());
        return "main";
    }

    public String apiPage(Model model) {
        List<ApiPojo> apiOptions = new ArrayList<>();
        for (Map<String, String> option: messageService.mainPage()) {
            apiOptions.add(new ApiPojo(option.get("path"), option.get("method"), option.get("description")));
        }
        model.addAttribute("options", apiOptions);
        return "api";
    }
}
