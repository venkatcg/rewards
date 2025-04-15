package com.customer.rewards.repository;

import com.customer.rewards.model.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCustomerId(String customerId);

    List<Transaction> findByCustomerIdAndTransactionDateBetween(String customerId, LocalDate start, LocalDate end);

    List<Transaction> findByTransactionDateBetween(LocalDate start, LocalDate end);

    @Query("SELECT t FROM Transaction t WHERE t.customerId = :customerId AND t.transactionDate BETWEEN :from AND :to")
    List<Transaction> findByCustomerIdAndDateRange(@Param("customerId") String customerId,
                                                   @Param("from") LocalDate from,
                                                   @Param("to") LocalDate to);
}
