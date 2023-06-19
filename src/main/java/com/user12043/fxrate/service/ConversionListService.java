package com.user12043.fxrate.service;

import com.github.f4b6a3.ulid.Ulid;
import com.user12043.fxrate.dto.ConversionResponse;
import com.user12043.fxrate.model.Transaction;
import com.user12043.fxrate.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ConversionListService {
    private final TransactionRepository transactionRepository;

    public ConversionListService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public List<ConversionResponse> findAllByDate(Long start, Long end) {
        final LocalDateTime startDate = LocalDateTime.ofInstant(new Date(start).toInstant(), ZoneOffset.UTC);
        final LocalDateTime endDate = LocalDateTime.ofInstant(new Date(end).toInstant(), ZoneOffset.UTC);
        return transactionRepository.findByDateBetween(startDate, endDate).stream().parallel().map(ConversionResponse::fromTransaction).toList();
    }

    @Transactional(readOnly = true)
    public ConversionResponse findById(String transactionId) {
        try {
            final Optional<Transaction> transactionOptional = transactionRepository.findById(Ulid.from(transactionId).toBytes());
            return transactionOptional.map(ConversionResponse::fromTransaction).orElse(null);
        } catch (IllegalArgumentException e) {
            // Catch exception when invalid ULID string provided
            return null;
        }
    }
}
