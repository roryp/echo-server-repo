package com.example.mcp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {

    private final Bot bot;

    public EchoController(Bot bot) {
        this.bot = bot;
    }

    @GetMapping("/echo")
    public String echo(@RequestParam String message) {
        return bot.chat(message);
    }
}
