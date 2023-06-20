package com.user12043.fxrate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.f4b6a3.ulid.Ulid;
import com.user12043.fxrate.model.ConversionTransaction;

import java.time.LocalDateTime;

/**
 * The DTO object to respond for conversion request
 *
 * @param id             transactionId as ULID string
 * @param date
 * @param baseCurrency
 * @param targetCurrency
 * @param amount
 * @param result
 */
public record ConversionResponse(
        String id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date,
        String baseCurrency,
        String targetCurrency,
        double amount,
        double result
) {
    public static ConversionResponse fromTransaction(ConversionTransaction conversionTransaction) {
        return new ConversionResponse(
                Ulid.from(conversionTransaction.getId()).toString(),
                conversionTransaction.getDate(),
                conversionTransaction.getBaseCurrency(),
                conversionTransaction.getTargetCurrency(),
                conversionTransaction.getAmount(),
                conversionTransaction.getResult()
        );
    }

    public static ConversionTransaction toTransaction(ConversionResponse dto) {
        return new ConversionTransaction(
                Ulid.from(dto.id).toBytes(),
                dto.date,
                dto.baseCurrency,
                dto.targetCurrency,
                dto.amount,
                dto.result);
    }
}
