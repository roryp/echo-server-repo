package com.example.mcp;

import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class EchoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EchoApplication.class, args);
    }

    @Bean
    public Bot bot() {
        StdioMcpTransport transport = new StdioMcpTransport.Builder()
            .command(List.of("docker", "run", "-i", "your-echo-tool-image"))
            .logEvents(true)
            .build();

        McpClient mcpClient = new DefaultMcpClient.Builder()
            .transport(transport)
            .build();

        McpToolProvider toolProvider = McpToolProvider.builder()
            .mcpClients(List.of(mcpClient))
            .build();

        // MCP-based Chat Model that directly forwards requests
        ChatLanguageModel mcpChatModel = message -> 
            toolProvider.tools().get(0).execute(message);

        return AiServices.builder(Bot.class)
            .chatLanguageModel(mcpChatModel)
            .toolProvider(toolProvider)
            .build();
    }
}
