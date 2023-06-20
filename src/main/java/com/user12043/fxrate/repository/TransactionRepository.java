package com.user12043.fxrate.repository;

import com.user12043.fxrate.model.ConversionTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The repository interface for interaction with Transaction in the database.
 */
public interface TransactionRepository extends JpaRepository<ConversionTransaction, byte[]> {
    // TODO: Implement custom save method which directly persists, for improved performance.
    //  default save() will issue select query before persisting. No need to do this for our case

    List<ConversionTransaction> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
