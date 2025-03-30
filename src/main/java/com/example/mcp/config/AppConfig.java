package com.example.mcp.config;

import com.example.mcp.Bot;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
public class AppConfig {

    @Value("${openai.api.key:${OPENAI_API_KEY:}}")
    private String openAiApiKey;

    @Value("${openai.timeout.seconds:60}")
    private long timeoutSeconds;

    @Bean
    public ChatLanguageModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .apiKey(openAiApiKey)
                .timeout(Duration.ofSeconds(timeoutSeconds))
                .modelName("gpt-4o-mini")
                .logRequests(true)
                .logResponses(true)
                .build();
    }
    
    @Bean
    public Bot bot(ChatLanguageModel chatLanguageModel) {
        return AiServices.builder(Bot.class)
                .chatLanguageModel(chatLanguageModel)
                .build();
    }
}