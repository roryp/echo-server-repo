package com.example.mcp.controller;

import com.example.mcp.service.CustomerQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/v1")
public class McpController {

    @Autowired
    private CustomerQueryService customerQueryService;

    @PostMapping("/mcp")
    public ResponseEntity<Map<String, Object>> processMcpRequest(@RequestBody Map<String, Object> request) {
        // Log the incoming request
        System.out.println("Received MCP request: " + request);
        
        // Extract the user's query from the MCP request
        String userQuery = extractUserQuery(request);
        
        // Process the query using the service
        Map<String, Object> preferences = customerQueryService.extractPreferences(userQuery);
        
        // Format the response according to MCP protocol
        Map<String, Object> response = formatMcpResponse(preferences);
        
        return ResponseEntity.ok(response);
    }
    
    private String extractUserQuery(Map<String, Object> request) {
        // Extract the query from the MCP request structure
        // This is simplified - you'll need to adapt based on actual MCP request format
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> inputs = (Map<String, Object>) request.get("inputs");
            if (inputs != null && inputs.containsKey("query")) {
                return (String) inputs.get("query");
            }
            return "No query provided";
        } catch (Exception e) {
            return "No query provided";
        }
    }
    
    private Map<String, Object> formatMcpResponse(Map<String, Object> preferences) {
        // Create a response that follows the MCP protocol
        // This is simplified - adapt based on MCP specs
        Map<String, Object> results = new HashMap<>();
        results.put("preferences", preferences);
        results.put("metadata", Map.of("confidence", 0.9));
        
        Map<String, Object> response = new HashMap<>();
        response.put("results", results);
        response.put("status", "success");
        
        return response;
    }
}