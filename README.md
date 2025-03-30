# LangChain4j MCP Echo Server with OpenAI Integration

This project demonstrates a **Spring Boot Echo Server** that uses LangChain4j **MCP (Model Context Protocol)** integration with OpenAI models to process user input.

*Last updated: March 30, 2025*

## Overview

This is a fully working backend that:

- Exposes a REST API at `/echo?message=...`
- Uses OpenAI's GPT models (currently configured with gpt-4o-mini) for processing
- Connects to an MCP-compliant server via Server-Sent Events (SSE)
- Processes messages through the LangChain4j toolchain

---

## Architecture Overview

### 1. Spring Boot App
- REST controller accepts requests on `/echo?message=...`
- Injects a `Bot` interface wired via LangChain4j's `AiServices`
- Bot implementation is powered by OpenAI's language model

### 2. LangChain4j MCP Integration
- Uses `HttpMcpTransport` with SSE configuration to communicate with the MCP server
- `DefaultMcpClient` handles the JSON-RPC 2.0 message passing
- `McpToolProvider` registers the tool with LangChain4j's runtime
- Messages are processed by OpenAI and routed through the MCP tools

### 3. OpenAI Integration
- Uses `OpenAiChatModel` to process messages
- Requires an OpenAI API key set as the `OPENAI_API_KEY` environment variable
- Currently configured to use the `gpt-4o-mini` model

---

## Prerequisites
- Java 17+
- Docker
- Maven
- Node.js (for the MCP server)
- OpenAI API key

---

## How to Run

### 1. Set Up the MCP Server
```bash
# Clone the MCP servers repository
git clone https://github.com/modelcontextprotocol/servers.git
cd servers/src/everything

# Install dependencies
npm install

# Start the server in SSE mode
node dist/sse.js
```

### 2. Set Your OpenAI API Key
```bash
# On Windows
set OPENAI_API_KEY=your_openai_api_key_here

# On Linux/macOS
export OPENAI_API_KEY=your_openai_api_key_here
```

### 3. Run the Spring Boot Server
```bash
# Option 1: Run with Maven
mvn spring-boot:run

# Option 2: Run the JAR file directly
java -jar target/echo-1.0-SNAPSHOT.jar
```

### 4. Docker Deployment
You can also run the application using Docker:

```bash
# Build the Docker image
docker build -t echo-server:latest .

# Run the container with your API key
docker run -p 8080:8080 -e OPENAI_API_KEY=your_openai_api_key_here echo-server:latest
```

Make sure the MCP server is accessible from within the Docker container. You may need to use host networking or adjust the MCP server URL in the configuration.

### 5. Test It
```bash
curl "http://localhost:8080/echo?message=Echo%20Hello%20world"
```

Expected response will be the message processed through OpenAI and the MCP tool.

---

## Key Files

### `EchoServer.java`
Sets up the OpenAI model, MCP transport, client, tool provider, and creates the LangChain4j-powered `Bot`.

### `Bot.java`
Defines a single-method interface that LangChain4j implements dynamically.

### `EchoController.java`
Handles HTTP requests and calls the `bot.chat(...)` method.

### `EchoServerController.java`
Provides additional endpoints for server management and health checks.

---

## Configuration Options

### OpenAI Model
You can adjust the model in `EchoServer.java`:
```java
.modelName("gpt-4o-mini") // Change to other models like "gpt-4" or "gpt-3.5-turbo"
```

### MCP Server Connection
The SSE URL can be modified in `EchoServer.java`:
```java
.sseUrl("http://localhost:3001/sse") // Change port or host as needed
.timeout(Duration.ofSeconds(60)) // Adjust timeout if needed
```

---

## Troubleshooting

### Common Issues

1. **OpenAI API Authentication Errors**
   - Verify your API key is set correctly in the environment
   - Check if your API key has sufficient permissions
   - Ensure your API key has not expired or been revoked

2. **MCP Server Connection Issues**
   - Ensure the MCP server is running at the configured address
   - Check the logs for connection timeouts or errors
   - Verify your network allows the connection
   - If running in Docker, make sure the container can reach the MCP server

3. **Response Timeouts**
   - The default timeout is 60 seconds; increase if necessary for complex queries
   - Consider using a more powerful OpenAI model for faster processing

4. **Java Memory Issues**
   - If you encounter `OutOfMemoryError`, adjust the JVM heap size with `-Xmx` flag
   - For Docker deployments, ensure the container has sufficient memory allocation

5. **Spring Boot Application Issues**
   - Check application logs for detailed error messages
   - Verify all required dependencies are in the pom.xml
   - Ensure the application port (default 8080) is not already in use

---

## Performance Tuning

For improved performance in production environments:

1. **OpenAI Model Selection**
   - Balance speed vs. capability by choosing the appropriate model
   - For high-traffic applications, consider using faster, smaller models

2. **Connection Pooling**
   - Configure appropriate HTTP connection pool sizes for OpenAI API calls

3. **Caching**
   - Implement response caching for frequently repeated queries
   - Consider using Spring Cache or a distributed cache like Redis

---

## Contributing

Contributions to improve the Echo Server are welcome! Here's how to contribute:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Implement your changes
4. Add tests if applicable
5. Commit your changes (`git commit -m 'Add some amazing feature'`)
6. Push to the branch (`git push origin feature/amazing-feature`)
7. Open a Pull Request

Please ensure your code follows the existing style and includes appropriate documentation.

---

## Why MCP?
The Model Context Protocol (MCP) allows language models to use external tools via a standardized interface. This integration enables your application to leverage both the power of OpenAI's language models and external tools defined in the MCP server.

---

## Credits
- [LangChain4j](https://github.com/langchain4j/langchain4j)
- [Model Context Protocol](https://github.com/modelcontextprotocol)
- [OpenAI](https://openai.com/)

---

## Next Steps
- Implement custom MCP tools for specialized functionality
- Add support for streaming responses
- Create more sophisticated agent workflows using LangChain4j
- Develop a frontend UI for interacting with the Echo Server
- Implement user authentication and rate limiting

---

If you have any questions or need assistance, please open an issue in this repository.

