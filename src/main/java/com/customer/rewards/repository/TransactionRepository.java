package com.customer.rewards.repository;

import com.customer.rewards.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCustomerId(String customerId);

    List<Transaction> findByCustomerIdAndTransactionDateBetween(String customerId, LocalDate start, LocalDate end);

    List<Transaction> findByTransactionDateBetween(LocalDate start, LocalDate end);
}
