package com.user12043.fxrate.service;

import com.github.f4b6a3.ulid.Ulid;
import com.user12043.fxrate.dto.ConversionResponse;
import com.user12043.fxrate.model.ConversionTransaction;
import com.user12043.fxrate.repository.TransactionRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service of conversion list API
 */
@Service
public class ConversionListService {
    private final TransactionRepository transactionRepository;

    public ConversionListService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    @Cacheable("listByDateCache") // TODO: set a TTL for cache
    public List<ConversionResponse> findAllByDate(Long start, Long end) {
        final LocalDateTime startDate = LocalDateTime.ofInstant(new Date(start).toInstant(), ZoneOffset.UTC);
        final LocalDateTime endDate = LocalDateTime.ofInstant(new Date(end).toInstant(), ZoneOffset.UTC);
        return transactionRepository.findByDateBetween(startDate, endDate).stream().parallel().map(ConversionResponse::fromTransaction).toList();
    }

    @Transactional(readOnly = true)
    @Cacheable("listByIdCache") // TODO: set a TTL for cache
    public ConversionResponse findById(String transactionId) {
        final Optional<ConversionTransaction> transactionOptional = transactionRepository.findById(Ulid.from(transactionId).toBytes());
        return transactionOptional.map(ConversionResponse::fromTransaction).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<ConversionResponse> findRecent() {
        return this.findAllByDate(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli() - Duration.ofMinutes(5).toMillis(), LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
    }
}
