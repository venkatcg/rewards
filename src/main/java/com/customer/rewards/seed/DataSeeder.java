package com.customer.rewards.seed;

import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final TransactionRepository transactionRepository;

    public DataSeeder(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Transaction> transactions = List.of(
                new Transaction(null, "C1", "Alice", 120.0, LocalDate.of(2024, 1, 15)),
                new Transaction(null, "C1", "Alice", 75.0, LocalDate.of(2024, 2, 10)),
                new Transaction(null, "C1", "Alice", 200.0, LocalDate.of(2024, 3, 5)),
                new Transaction(null, "C2", "Bob", 95.0, LocalDate.of(2024, 1, 20)),
                new Transaction(null, "C2", "Bob", 130.0, LocalDate.of(2024, 2, 17)),
                new Transaction(null, "C2", "Bob", 60.0, LocalDate.of(2024, 3, 22))
        );

        transactionRepository.saveAll(transactions);
    }
}
