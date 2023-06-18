package com.user12043.fxrate.client;

import com.user12043.fxrate.dto.external.ExchangeConvertResponse;
import com.user12043.fxrate.dto.external.ExchangePairResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class ExchangeAPI {
    @Value("${exchange.api.basePath}")
    private String exchangeApiPath;

    @Bean
    private RestTemplate getTemplate() {
        return new RestTemplate();
    }

    public ExchangePairResponse fetchRateFromRemote(String baseCurrency, String targetCurrency) throws HttpClientErrorException {
        final ResponseEntity<ExchangePairResponse> responseEntity = getTemplate().getForEntity(
                "%s/pair/%s/%s".formatted(exchangeApiPath, baseCurrency, targetCurrency), ExchangePairResponse.class);
        return responseEntity.getBody();
    }

    public ExchangeConvertResponse fetchConversionFromRemote(String baseCurrency, String targetCurrency, double amount) throws HttpClientErrorException {
        final ResponseEntity<ExchangeConvertResponse> responseEntity = getTemplate().getForEntity(
                "%s/pair/%s/%s/%f".formatted(exchangeApiPath, baseCurrency, targetCurrency, amount), ExchangeConvertResponse.class);
        return responseEntity.getBody();
    }
}
