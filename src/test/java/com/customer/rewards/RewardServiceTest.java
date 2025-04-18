package com.customer.rewards;

import com.customer.rewards.dto.CustomerRewardDTO;
import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.TransactionRepository;
import com.customer.rewards.service.RewardService;
import com.customer.rewards.util.RewardCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RewardServiceTest {
    @Mock
    private TransactionRepository repo;
    private RewardService service;
    private RewardCalculator rewardCalculator;

    @BeforeEach
    void setUp() {
        this.rewardCalculator = new RewardCalculator();
        this.service = new RewardService(this.rewardCalculator, this.repo);
    }

    @Test
    void testCalculatePoints() {
        assertEquals(90, rewardCalculator.calculatePoints(120));
        assertEquals(0, rewardCalculator.calculatePoints(50));
        assertEquals(30, rewardCalculator.calculatePoints(80));
    }

    @Test
    void testGetCustomerRewards() {
        Transaction t1 = new Transaction(null, "C1", "Alice", 120, LocalDate.of(2024, 1, 15));
        Transaction t2 = new Transaction(null, "C1", "Alice", 80, LocalDate.of(2024, 1, 16));

        Mockito.when(repo.findByCustomerId("C1")).thenReturn(List.of(t1, t2));
        CustomerRewardDTO result = service.getCustomerRewards("C1");

        assertEquals("C1", result.getCustomerId());
        assertEquals(90 + 30, result.getTotalPoints());
    }
}