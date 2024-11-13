package com.c1se62.clinic_booking.controller;

import com.c1se62.clinic_booking.service.ChatGPTService.ChatGPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatGPTController {

    @Autowired
    private ChatGPTService chatGPTService;

    @PostMapping("/advice")
    public String getAdvice(@RequestBody String prompt) {
        return chatGPTService.getChatGPTResponse(prompt);
    }
}
