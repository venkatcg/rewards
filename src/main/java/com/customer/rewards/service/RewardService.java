package com.customer.rewards.service;

import com.customer.rewards.dto.*;
import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.TransactionRepository;
import com.customer.rewards.util.RewardCalculator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
public class RewardService {
    private final TransactionRepository repo;

    public RewardService(TransactionRepository repo) {
        this.repo = repo;
    }

    public int calculatePoints(double amount) {
        if (amount <= 50) return 0;
        if (amount <= 100) return (int) (amount - 50);
        return (int) ((amount - 100) * 2 + 50);
    }

    public CustomerRewardDTO getCustomerRewards(String customerId) {
        List<Transaction> txns = repo.findByCustomerId(customerId);
        Map<String, Integer> monthlyPoints = new HashMap<>();
        int totalPoints = 0;
        for (Transaction txn : txns) {
            String month = txn.getTransactionDate().getMonth().toString();
            int points = calculatePoints(txn.getAmount());
            monthlyPoints.merge(month, points, Integer::sum);
            totalPoints += points;
        }
        CustomerRewardDTO dto = new CustomerRewardDTO();
        dto.setCustomerId(customerId);
        dto.setCustomerName(txns.isEmpty() ? "" : txns.get(0).getCustomerName());
        dto.setMonthlyPoints(monthlyPoints);
        dto.setTotalPoints(totalPoints);
        return dto;
    }

    public MonthlyTransactionDTO getMonthlyTransactions(String customerId, String ym) {
        YearMonth yearMonth = YearMonth.parse(ym);
        LocalDate start = yearMonth.atDay(1);
        LocalDate end = yearMonth.atEndOfMonth();
        List<Transaction> txns = repo.findByCustomerIdAndTransactionDateBetween(customerId, start, end);
        List<TransactionDTO> list = txns.stream().map(t -> {
            TransactionDTO dto = new TransactionDTO();
            dto.setAmount(t.getAmount());
            dto.setTransactionDate(t.getTransactionDate());
            dto.setPoints(calculatePoints(t.getAmount()));
            return dto;
        }).toList();
        MonthlyTransactionDTO result = new MonthlyTransactionDTO();
        result.setCustomerId(customerId);
        result.setMonth(ym);
        result.setTransactions(list);
        return result;
    }

    public DateRangeRewardsDTO getRewardsInRange(String from, String to) {
        LocalDate start = LocalDate.parse(from + "-01");
        LocalDate end = YearMonth.parse(to).atEndOfMonth();
        List<Transaction> txns = repo.findByTransactionDateBetween(start, end);

        Map<String, Map<String, CustomerMonthlyPointsDTO>> grouped = new HashMap<>();

        for (Transaction t : txns) {
            String month = t.getTransactionDate().getMonth().toString();
            grouped.putIfAbsent(t.getCustomerId(), new HashMap<>());
            Map<String, CustomerMonthlyPointsDTO> customerMap = grouped.get(t.getCustomerId());
            customerMap.putIfAbsent(month, new CustomerMonthlyPointsDTO(t.getCustomerId(), t.getCustomerName(), month, 0));
            CustomerMonthlyPointsDTO dto = customerMap.get(month);
            dto.setPoints(dto.getPoints() + calculatePoints(t.getAmount()));
        }

        List<CustomerMonthlyPointsDTO> list = grouped.values().stream()
                .flatMap(m -> m.values().stream()).toList();

        DateRangeRewardsDTO result = new DateRangeRewardsDTO();
        result.setFrom(from);
        result.setTo(to);
        result.setRewards(list);
        return result;
    }
}