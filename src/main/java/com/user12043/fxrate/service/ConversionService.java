package com.user12043.fxrate.service;

import com.user12043.fxrate.client.ExchangeAPI;
import com.user12043.fxrate.dto.ConversionResponse;
import com.user12043.fxrate.dto.external.ExchangeConvertResponse;
import com.user12043.fxrate.model.Transaction;
import com.user12043.fxrate.repository.TransactionRepository;
import com.user12043.fxrate.util.TransactionUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;

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
        Transaction transaction = new Transaction(TransactionUtil.next().toBytes(),
                new Date(Calendar.getInstance().getTimeInMillis()),
                baseCurrency,
                targetCurrency,
                amount,
                apiResponse.conversionResult());
        transactionRepository.save(transaction);
        return ConversionResponse.fromTransaction(transaction);
    }
}
