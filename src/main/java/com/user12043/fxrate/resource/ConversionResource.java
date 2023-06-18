package com.user12043.fxrate.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/convert")
public class ConversionResource {
    @Value("${exchange.api.basePath}")
    private String exchangeApiPath;


    @GetMapping
    public String convert() {
        return """
                {"apiKey":"%s"}""".formatted(exchangeApiPath);
    }
}
