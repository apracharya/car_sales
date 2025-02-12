package com.apr.car_sales.service.chatbot;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import org.springframework.stereotype.Service;

@Service
public class OpenAiChatServiceImpl implements OpenAiChatService {

    private final OpenAiClientService openAiClientService;

    public OpenAiChatServiceImpl(OpenAiClientService openAiClientService) {
        this.openAiClientService = openAiClientService;
    }

    @Override
    public String getChatResponse(String userMessage) {
        CompletionRequest request = CompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .prompt(userMessage)
                .maxTokens(100)
                .temperature(0.7)
                .build();

        CompletionResult result = openAiClientService.getOpenAiService().createCompletion(request);
        return result.getChoices().get(0).getText();
    }
}
