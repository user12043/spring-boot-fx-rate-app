package com.user12043.fxrate.service;

import com.user12043.fxrate.client.ExchangeAPI;
import com.user12043.fxrate.dto.RateResponse;
import com.user12043.fxrate.dto.external.ExchangePairResponse;
import org.springframework.stereotype.Service;

/**
 * Service of exchange rate API
 */
@Service
public class RateService {
    private final ExchangeAPI exchangeAPI;

    public RateService(ExchangeAPI exchangeAPI) {
        this.exchangeAPI = exchangeAPI;
    }

    public RateResponse getRate(String baseCurrency, String targetCurrency) {
        final ExchangePairResponse apiResponse = exchangeAPI.fetchRateFromRemote(baseCurrency, targetCurrency);
        return new RateResponse(apiResponse.baseCode(), apiResponse.targetCode(), apiResponse.conversionRate());
    }
}
