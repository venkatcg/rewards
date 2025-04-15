package com.customer.rewards.service;

import com.customer.rewards.dto.CustomerRewardDTO;
import com.customer.rewards.dto.MonthlyTransactionDTO;
import com.customer.rewards.dto.DateRangeRewardsDTO123;
import com.customer.rewards.dto.CustomerMonthlyPointsDTO;
import com.customer.rewards.dto.TransactionDTO;
import com.customer.rewards.dto.DateRangeRewardsDTO;
import com.customer.rewards.model.Transaction;
import com.customer.rewards.repository.TransactionRepository;
import com.customer.rewards.util.RewardCalculator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardService {
    private final TransactionRepository transactionRepository;
    private final RewardCalculator rewardCalculator;

    public RewardService(RewardCalculator rewardCalculator,TransactionRepository transactionRepository) {

        this.transactionRepository = transactionRepository;
        this.rewardCalculator=rewardCalculator;
    }

     public CustomerRewardDTO getCustomerRewards(String customerId) {
        List<Transaction> txns = transactionRepository.findByCustomerId(customerId);
        Map<String, Integer> monthlyPoints = new HashMap<>();
        int totalPoints = 0;
        for (Transaction txn : txns) {
            String month = txn.getTransactionDate().getMonth().toString();
            int points = rewardCalculator.calculatePoints(txn.getAmount());
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
        List<Transaction> txns = transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, start, end);
        List<TransactionDTO> list = txns.stream().map(t -> {
            TransactionDTO dto = new TransactionDTO();
            dto.setAmount(t.getAmount());
            dto.setTransactionDate(t.getTransactionDate());
            dto.setPoints(rewardCalculator.calculatePoints(t.getAmount()));
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
        List<Transaction> txns = transactionRepository.findByTransactionDateBetween(start, end);

        Map<String, Map<String, CustomerMonthlyPointsDTO>> grouped = new HashMap<>();

        for (Transaction t : txns) {
            String month = t.getTransactionDate().getMonth().toString();
            grouped.putIfAbsent(t.getCustomerId(), new HashMap<>());
            Map<String, CustomerMonthlyPointsDTO> customerMap = grouped.get(t.getCustomerId());
            customerMap.putIfAbsent(month, new CustomerMonthlyPointsDTO(t.getCustomerId(), t.getCustomerName(), month, 0));
            CustomerMonthlyPointsDTO dto = customerMap.get(month);
            dto.setPoints(dto.getPoints() + rewardCalculator.calculatePoints(t.getAmount()));
        }

        List<CustomerMonthlyPointsDTO> list = grouped.values().stream()
                .flatMap(m -> m.values().stream()).toList();

        DateRangeRewardsDTO result = new DateRangeRewardsDTO();
        result.setFrom(from);
        result.setTo(to);
        result.setRewards(list);
        return result;
    }
    public DateRangeRewardsDTO123 getCustomerRewardsInRange(String customerId, String from, String to) {
        YearMonth start = YearMonth.parse(from);
        YearMonth end = YearMonth.parse(to);

        List<CustomerMonthlyPointsDTO> monthlyRewards = calculateMonthlyRewards(customerId, start, end);

        int totalRewards = monthlyRewards.stream()
                .mapToInt(CustomerMonthlyPointsDTO::getPoints)
                .sum();

        return new DateRangeRewardsDTO123(customerId, from, to, totalRewards, monthlyRewards);
    }
    public List<CustomerMonthlyPointsDTO> calculateMonthlyRewards(String customerId, YearMonth start, YearMonth end) {
        List<Transaction> transactions = transactionRepository.findByCustomerIdAndDateRange(
                customerId,
                start.atDay(1),
                end.atEndOfMonth()
        );

        // Group by YearMonth
        Map<YearMonth, List<Transaction>> groupedByMonth = transactions.stream()
                .collect(Collectors.groupingBy(tx -> YearMonth.from(tx.getTransactionDate())));

        List<CustomerMonthlyPointsDTO> result = new ArrayList<>();

        for (YearMonth month : groupedByMonth.keySet()) {
            List<Transaction> monthlyTxs = groupedByMonth.get(month);
            int monthlyPoints = 0;

            for (Transaction tx : monthlyTxs) {
                monthlyPoints += rewardCalculator.calculatePoints(tx.getAmount());
            }

            result.add(new CustomerMonthlyPointsDTO(month.toString(), null,null,monthlyPoints));
        }

        // Sort by month
        result.sort(Comparator.comparing(CustomerMonthlyPointsDTO::getMonth));

        return result;
    }


}