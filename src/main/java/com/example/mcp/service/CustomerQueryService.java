package com.example.mcp.service;

import dev.langchain4j.model.chat.ChatLanguageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerQueryService {

    @Autowired
    private ChatLanguageModel chatModel;
    
    public Map<String, Object> extractPreferences(String userQuery) {
        // For demo purposes, extracting preferences with a simple prompt
        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append("Extract travel preferences from the user's query.\n\n");
        promptBuilder.append("The response should be in JSON format with these fields:\n");
        promptBuilder.append("- destination: where they want to go (if mentioned)\n");
        promptBuilder.append("- duration: how long they want to travel (if mentioned)\n");
        promptBuilder.append("- budget: budget constraints (if mentioned)\n");
        promptBuilder.append("- activities: preferred activities (if mentioned)\n");
        promptBuilder.append("- accommodation: preferred accommodation (if mentioned)\n");
        promptBuilder.append("- transportation: preferred transportation (if mentioned)\n\n");
        promptBuilder.append("User query: ").append(userQuery);
        
        // Generate response using the chat model
        String responseText = chatModel.chat(promptBuilder.toString());
        
        // In a real implementation, parse the JSON response
        // For now, just return a simplified map
        Map<String, Object> preferences = new HashMap<>();
        preferences.put("rawResponse", responseText);
        preferences.put("extractedFromQuery", userQuery);
        
        return preferences;
    }
}