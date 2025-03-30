package com.example.mcp;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EchoServerController {

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public Map<String, Object> echo(HttpServletRequest request,
                                    @RequestBody(required = false) String body,
                                    @RequestHeader HttpHeaders headers) {

        return Map.of(
                "method", request.getMethod(),
                "headers", headers.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)),
                "query", request.getQueryString(),
                "path", request.getRequestURI(),
                "body", body != null ? body : ""
        );
    }
}
