package com.customer.rewards;

import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository repo;

    @Test
    void testFindByCustomerId() {
        Transaction t = new Transaction(null, "C3", "Eve", 150, LocalDate.of(2024, 2, 1));
        repo.save(t);

        List<Transaction> list = repo.findByCustomerId("C3");
        assertFalse(list.isEmpty());
        assertEquals("C3", list.get(0).getCustomerId());
    }

    @Test
    void testFindByDateRange() {
        Transaction t = new Transaction(null, "C4", "Sam", 100, LocalDate.of(2024, 1, 10));
        repo.save(t);

        LocalDate from = LocalDate.of(2024, 1, 1);
        LocalDate to = LocalDate.of(2024, 1, 31);
        List<Transaction> txns = repo.findByTransactionDateBetween(from, to);
        assertTrue(txns.stream().anyMatch(x -> x.getCustomerId().equals("C4")));
    }
}