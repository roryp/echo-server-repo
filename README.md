# Java Customer Query Agent with Spring Boot 3 and LangChain4j MCP

This project implements a **Java Customer Query Agent** that uses Spring Boot 3, LangChain4j, and OpenAI integration to process customer queries and extract structured information from them.

*Last updated: March 30, 2025*

## Overview

This is a fully working backend service that:

- Exposes a REST API at `/echo?message=...` for simple echo functionality
- Provides an MCP-compatible endpoint at `/v1/mcp` for structured data extraction
- Uses OpenAI's GPT models (currently configured with gpt-4o-mini) for natural language processing
- Extracts structured information like travel preferences from user queries
- Provides Docker containerization for easy deployment

## Architecture Overview

### 1. Spring Boot Application
- **EchoController**: Handles requests at `/echo?message=...` and returns AI-processed responses
- **McpController**: Processes structured MCP protocol requests and extracts customer preferences
- **CustomerQueryService**: Provides business logic for extracting information from customer queries
- **Bot Interface**: Implemented via LangChain4j's `AiServices` for simple AI interaction

### 2. LangChain4j Integration
- Uses `ChatLanguageModel` to interact with OpenAI's language models
- Leverages LangChain4j's `AiServices` framework for simplified AI service creation
- Structured for extensibility with additional AI capabilities

### 3. OpenAI Integration
- Uses `OpenAiChatModel` to process messages
- Requires an OpenAI API key set as the `OPENAI_API_KEY` environment variable
- Currently configured to use the `gpt-4o-mini` model for optimal performance/cost balance

---

## Prerequisites
- Java 21+
- Maven
- Docker
- OpenAI API key

---

## How to Run

### 1. Set Your OpenAI API Key
```bash
# On Windows
set OPENAI_API_KEY=your_openai_api_key_here

# On Linux/macOS
export OPENAI_API_KEY=your_openai_api_key_here
```

### 2. Build and Run the Spring Boot Server
```bash
# Build with Maven
mvn clean package

# Run the JAR file
java -jar target/echo-1.0-SNAPSHOT.jar
```

### 3. Docker Deployment Options

#### Using Docker directly:
```bash
# Build the Docker image
docker build -t customer-query-agent:latest .

# Run the container
docker run -p 8080:8080 -e OPENAI_API_KEY=your_openai_api_key_here customer-query-agent:latest
```

#### Using Docker Compose (recommended):
```bash
# Make sure OPENAI_API_KEY is set in your environment
docker-compose up
```

### 4. Test the Service

#### Test the Echo Endpoint:
```bash
curl "http://localhost:8080/echo?message=Tell%20me%20about%20Paris"
```

#### Test the MCP Endpoint:
```bash
curl -X POST http://localhost:8080/v1/mcp \
  -H "Content-Type: application/json" \
  -d '{
    "inputs": {
      "query": "I want to go to Paris for 5 days with my family, budget around $3000"
    }
  }'
```

---

## Key Files

### `CustomerQueryApplication.java`
The main Spring Boot application entry point.

### `Bot.java`
Simple interface that defines the chat interaction contract.

### `EchoController.java`
Handles HTTP requests to the `/echo` endpoint.

### `McpController.java`
Processes MCP protocol requests for structured information extraction.

### `CustomerQueryService.java`
Implements the business logic for extracting customer preferences.

### `AppConfig.java`
Configures Spring beans for the application, including the OpenAI model and Bot implementation.

---

## Configuration Options

### Application Properties
The application can be configured through `application.properties`:
```properties
# Server configuration
server.port=8080
spring.application.name=customer-query-java

# OpenAI configuration
openai.api.key=${OPENAI_API_KEY}
openai.timeout.seconds=60
```

### OpenAI Model
You can adjust the model in `AppConfig.java`:
```java
.modelName("gpt-4o-mini") // Change to other models like "gpt-4" or "gpt-3.5-turbo"
```

---

## Troubleshooting

### Common Issues

1. **OpenAI API Authentication Errors**
   - Verify your API key is set correctly in the environment
   - Check if your API key has sufficient permissions and quota

2. **Application Startup Issues**
   - Ensure port 8080 is not already in use
   - Check application logs for detailed error messages
   - Verify Java 21 is installed and configured correctly

3. **Docker Issues**
   - Ensure Docker is running
   - Check if the required ports are available
   - Verify the OPENAI_API_KEY environment variable is passed to the container

---

## Extending the Application

### Adding New AI Capabilities
1. Define new service interfaces
2. Implement them using LangChain4j's `AiServices`
3. Create corresponding controller endpoints

### Customizing the Preference Extraction
Modify the prompt in `CustomerQueryService.java` to extract different types of preferences or information.

### Supporting Additional Language Models
Extend the `AppConfig.java` to support alternative language model providers.

---

## Performance Considerations

- The application is designed for moderate throughput scenarios
- For high-traffic applications:
  - Consider implementing caching mechanisms
  - Use more efficient models for simple queries
  - Configure appropriate connection pool settings

---

## Security Notes

- Never hardcode OpenAI API keys in the code
- In production, use a proper secrets management solution
- Consider implementing rate limiting for public-facing APIs
- Add appropriate authentication for API endpoints

---

## Credits
- [Spring Boot](https://spring.io/projects/spring-boot)
- [LangChain4j](https://github.com/langchain4j/langchain4j)
- [OpenAI](https://openai.com/)

---

If you have any questions or need assistance, please open an issue in this repository.

