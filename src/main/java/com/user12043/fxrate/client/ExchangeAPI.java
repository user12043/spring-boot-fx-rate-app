package com.user12043.fxrate.client;

import com.user12043.fxrate.dto.external.ExchangeConvertResponse;
import com.user12043.fxrate.dto.external.ExchangePairResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * The client for third party exchangerate-api.com
 */
@Component
public class ExchangeAPI {
    private final RestTemplate template = new RestTemplate();

    /**
     * Base request URL
     */
    @Value("${exchange.api.basePath}")
    private String exchangeApiPath;

    /**
     * Fetches the exchange rate for given currencies
     *
     * @param baseCurrency   The base currency to convert from
     * @param targetCurrency The target currency to convert to
     * @return Exchange rate
     * @throws HttpClientErrorException When there is a problem with connection to exchangerate-api.com
     */
    @Cacheable(value = "rateResponseCache")
    public ExchangePairResponse fetchRateFromRemote(String baseCurrency, String targetCurrency) throws HttpClientErrorException {
        final ResponseEntity<ExchangePairResponse> responseEntity = template.getForEntity(
                "%s/pair/%s/%s".formatted(exchangeApiPath, baseCurrency, targetCurrency), ExchangePairResponse.class);
        return responseEntity.getBody();
    }

    /**
     * Fetches the amount of given currency in another currency
     *
     * @param baseCurrency   The base currency to convert from
     * @param targetCurrency The target currency to convert to
     * @param amount         Amount of base currency to convert
     * @return The corresponding amount of target currency in given base currency
     * @throws HttpClientErrorException When there is a problem with connection to exchangerate-api.com
     */
    @Cacheable("conversionResponseCache")
    public ExchangeConvertResponse fetchConversionFromRemote(String baseCurrency, String targetCurrency, double amount) throws HttpClientErrorException {
        final ResponseEntity<ExchangeConvertResponse> responseEntity = template.getForEntity(
                "%s/pair/%s/%s/%f".formatted(exchangeApiPath, baseCurrency, targetCurrency, amount), ExchangeConvertResponse.class);
        return responseEntity.getBody();
    }
}
