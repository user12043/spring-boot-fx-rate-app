package com.user12043.fxrate.service;

import com.user12043.fxrate.client.ExchangeAPI;
import com.user12043.fxrate.dto.ConversionResponse;
import com.user12043.fxrate.dto.external.ExchangeConvertResponse;
import org.springframework.stereotype.Service;

@Service
public class ConversionService {
    private final ExchangeAPI exchangeAPI;

    public ConversionService(ExchangeAPI exchangeAPI) {
        this.exchangeAPI = exchangeAPI;
    }

    public ConversionResponse convert(String baseCurrency, String targetCurrency, double amount) {
        final ExchangeConvertResponse apiResponse = exchangeAPI.fetchConversionFromRemote(baseCurrency, targetCurrency, amount);
        return new ConversionResponse(baseCurrency, targetCurrency, apiResponse.conversionResult());
    }
}
