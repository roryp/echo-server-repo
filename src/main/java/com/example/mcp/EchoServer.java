package com.example.mcp;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;

import java.time.Duration;
import java.util.List;

public class EchoServer {

    /**
     * This example uses the `server-everything` MCP server that showcases some aspects of the MCP protocol.
     * In particular, we use its 'add' tool that adds two numbers.
     * <p>
     * Before running this example, you need to start the `everything` server in SSE mode on localhost:3001.
     * Check out https://github.com/modelcontextprotocol/servers/tree/main/src/everything
     * and run `npm install` and `node dist/sse.js`.
     * <p>
     * Of course, feel free to swap out the server with any other MCP server.
     * <p>
     * Run the example and check the logs to verify that the model used the tool.
     */
    public static void main(String[] args) throws Exception {

        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey(System.getenv("OPENAI_API_KEY"))
                .modelName("gpt-4o-mini")
                .logRequests(true)
                .logResponses(true)
                .build();

        McpTransport transport = new HttpMcpTransport.Builder()
                .sseUrl("http://localhost:3001/sse")
                .timeout(Duration.ofSeconds(60))
                .logRequests(true)
                .logResponses(true)
                .build();

        McpClient mcpClient = new DefaultMcpClient.Builder()
                .transport(transport)
                .build();

        ToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(List.of(mcpClient))
                .build();

        Bot bot = AiServices.builder(Bot.class)
                .chatLanguageModel(model)
                .toolProvider(toolProvider)
                .build();
        try {
            String response = bot.chat("Echo \"Hello world\"");
            System.out.println(response);
        } finally {
            mcpClient.close();
        }
    }
}
