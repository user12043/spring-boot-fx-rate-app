package com.user12043.fxrate.repository;

import com.user12043.fxrate.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, byte[]> {
    // TODO: Implement custom save method which directly persists, for improved performance.
    //  default save() will issue select query before persisting. No need to do this for our case

    List<Transaction> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
