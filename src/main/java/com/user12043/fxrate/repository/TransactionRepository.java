package com.user12043.fxrate.repository;

import com.user12043.fxrate.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, byte[]> {
}
