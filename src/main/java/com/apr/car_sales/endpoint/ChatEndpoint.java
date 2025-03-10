package com.apr.car_sales.endpoint;

import com.apr.car_sales.service.chatbot.OpenAiChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatEndpoint {
    private final OpenAiChatService openAiChatService;

    @Autowired
    public ChatEndpoint(OpenAiChatService openaiChatService) {
        this.openAiChatService = openaiChatService;
    }

    @PostMapping
    public String chat(@RequestBody String userInput) {
        try {
            return openAiChatService.getChatResponse(userInput);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
