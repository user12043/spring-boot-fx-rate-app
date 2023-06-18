package com.user12043.fxrate.resource;

import com.user12043.fxrate.dto.ExchangeStandardResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/rate")
public class RateResource {
    @Value("${exchange.api.basePath}")
    private String exchangeApiPath;

    @GetMapping
    public String getRate(@RequestParam("baseCurrency") String baseCurrency) {
        RestTemplate template = new RestTemplate();
        final ResponseEntity<ExchangeStandardResponse> responseEntity = template.getForEntity(
                "%s/%s".formatted(exchangeApiPath, baseCurrency), ExchangeStandardResponse.class);
        return """
                {"apiKey":"%s"}""".formatted(exchangeApiPath);
    }
}
