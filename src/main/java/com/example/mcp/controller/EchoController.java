package com.example.mcp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mcp.echo.Bot;

@RestController
public class EchoController {

    @Autowired
    private Bot bot;

    @GetMapping("/echo")
    public ResponseEntity<String> echo(@RequestParam String message) {
        // Log the incoming message
        System.out.println("Received echo request with message: " + message);
        
        // Process the message using the bot interface
        String response = bot.chat(message);
        
        return ResponseEntity.ok(response);
    }
}