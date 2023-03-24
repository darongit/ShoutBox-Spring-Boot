package com.example.shoutboxapp.messagefront;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FrontController {
    private FrontService frontService;
    @Autowired
    public FrontController(FrontService frontService) {
        this.frontService = frontService;
    }

    @GetMapping("/")
    public String mainPage(
            Model model,
            @RequestParam(name = "author", defaultValue = "") String author,
            @RequestParam(name = "message", defaultValue = "") String message) {
        return frontService.mainPage(model, author, message);
    }

    @GetMapping("/apimap")
    public String apiPage(Model model) {
        return frontService.apiPage(model);
    }
}
