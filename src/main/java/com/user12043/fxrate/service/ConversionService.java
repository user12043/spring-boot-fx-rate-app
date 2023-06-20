package com.user12043.fxrate.service;

import com.user12043.fxrate.client.ExchangeAPI;
import com.user12043.fxrate.dto.ConversionResponse;
import com.user12043.fxrate.dto.external.ExchangeConvertResponse;
import com.user12043.fxrate.repository.TransactionRepository;
import com.user12043.fxrate.util.TransactionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service of conversion API
 */
@Service
public class ConversionService {
    private final ExchangeAPI exchangeAPI;

    private final TransactionRepository transactionRepository;

    public ConversionService(ExchangeAPI exchangeAPI, TransactionRepository transactionRepository) {
        this.exchangeAPI = exchangeAPI;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public ConversionResponse convert(String baseCurrency, String targetCurrency, double amount) {
        final ExchangeConvertResponse apiResponse = exchangeAPI.fetchConversionFromRemote(baseCurrency, targetCurrency, amount);
        ConversionResponse response = new ConversionResponse(TransactionUtil.next().toString(),
                LocalDateTime.now(),
                baseCurrency,
                targetCurrency,
                amount,
                apiResponse.conversionResult());
        transactionRepository.save(ConversionResponse.toTransaction(response));
        return response;
    }
}
