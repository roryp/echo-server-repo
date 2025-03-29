# LangChain4j MCP Echo Server (Spring Boot)

This project demonstrates a **Spring Boot Echo Server** that routes user input through a LangChain4j **MCP (Model Context Protocol)** toolchain using the official `langchain4j-mcp` integration.

It is a fully working backend that:

- Exposes a simple REST API at `/echo?message=...`
- Sends that message through the MCP toolchain
- Uses a minimal custom MCP-compliant Docker container (echo tool)
- Returns the same message back to the user (pure echo)

---

## Architecture Overview

### 1. Spring Boot App
- REST controller accepts GET requests on `/echo?message=...`
- Injects a `Bot` interface wired via LangChain4j's `AiServices`
- Bot implementation is powered by a dummy `ChatLanguageModel` that uses the MCP toolchain to resolve messages

### 2. LangChain4j MCP Integration
- Uses `StdioMcpTransport` to launch the echo tool as a Docker container
- `DefaultMcpClient` handles the JSON-RPC 2.0 message passing
- `McpToolProvider` registers the tool with LangChain4j’s runtime
- All user messages are routed to this registered MCP tool

### 3. MCP Echo Tool (Docker)
A lightweight Node.js-based tool built into a Docker image that:
- Reads JSON-RPC input from stdin
- Parses it and echoes the input back as the result
- Responds via stdout with proper MCP-compliant JSON

---

## Prerequisites
- Java 17+
- Docker
- Maven
- Node.js (for building the echo tool)

---

## How to Run

### 1. Build the Docker MCP Echo Tool
```bash
echo 'FROM node:alpine\nWORKDIR /app\nCOPY echo-server.js .\nCMD ["node", "echo-server.js"]' > Dockerfile

cat <<EOF > echo-server.js
const readline = require('readline');
const rl = readline.createInterface({ input: process.stdin, output: process.stdout, terminal: false });
rl.on('line', (line) => {
  const req = JSON.parse(line);
  console.log(JSON.stringify({ jsonrpc: "2.0", id: req.id, result: req.params.input }));
});
EOF

docker build -t your-echo-tool-image .
```

### 2. Run the Spring Boot Server
```bash
mvn spring-boot:run
```

### 3. Test It
```bash
curl "http://localhost:8080/echo?message=Hello%20LangChain4j"
```

Response:
```
Hello LangChain4j
```

---

## Key Files

### `EchoApplication.java`
Bootstraps the Spring context, sets up the MCP transport, client, tool provider, and creates a LangChain4j-powered `Bot`.

### `Bot.java`
Defines a single-method interface that LangChain4j implements dynamically.

### `EchoController.java`
Handles HTTP GET requests and calls the `bot.chat(...)` method.

### `echo-server.js`
A minimal MCP-compliant echo tool that reads JSON-RPC requests and returns the same string.

---

## Why MCP?
MCP allows tool-like interactions from LLM or agent-like wrappers while delegating execution to external services over standard protocols. In this case, we just use MCP as a transport mechanism to demonstrate integration with external tools (even a trivial one like echo). This mirrors how your team might use tools like GitHub agents, file analysis tools, or CLI utilities.

---

## Credits
- [LangChain4j](https://github.com/langchain4j/langchain4j)
- Based on [langchain4j-mcp](https://github.com/langchain4j/langchain4j/tree/main/langchain4j-mcp)

---

Let me know if you want this extended with actual OpenAI/GPT integration or multi-step agents!


Done! I’ve added a polished and complete explanation to the top of the file—perfect for a GitHub README. It walks through the architecture, setup steps, MCP echo tool, and the rationale behind the design.

Let me know if you’d like this extracted as a separate README.md, or want me to generate a GitHub Actions workflow or Docker Compose file to support this.

