package com.user12043.fxrate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.f4b6a3.ulid.Ulid;
import com.user12043.fxrate.model.Transaction;

import java.time.LocalDateTime;

public record ConversionResponse(
        String id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date,
        String baseCurrency,
        String targetCurrency,
        double amount,
        double result
) {
    public static ConversionResponse fromTransaction(Transaction transaction) {
        return new ConversionResponse(
                Ulid.from(transaction.getId()).toString(),
                transaction.getDate(),
                transaction.getBaseCurrency(),
                transaction.getTargetCurrency(),
                transaction.getAmount(),
                transaction.getResult()
        );
    }

    public static Transaction toTransaction(ConversionResponse dto) {
        return new Transaction(
                Ulid.from(dto.id).toBytes(),
                dto.date,
                dto.baseCurrency,
                dto.targetCurrency,
                dto.amount,
                dto.result);
    }
}
