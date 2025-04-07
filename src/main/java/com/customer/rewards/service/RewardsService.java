package com.customer.rewards.service;

import com.customer.rewards.model.Transaction;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardsService {

    public Map<String, Map<String, Integer>> computeRewards(List<Transaction> transactions) {
        Map<String, Map<String, Integer>> result = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        for (Transaction transaction : transactions) {
            String customer = transaction.getCustomer();
            String month = transaction.getDate().format(formatter);
            int points = calculatePoints(transaction.getAmount());
            result.putIfAbsent(customer, new HashMap<>());
            Map<String, Integer> customerData = result.get(customer);
            customerData.put(month, customerData.getOrDefault(month, 0) + points);
            customerData.put("total", customerData.getOrDefault("total", 0) + points);
        }
        return result;
    }

    public int calculatePoints(double amount) {
        if (amount <= 50) return 0;
        else if (amount < 100) return (int) (amount - 50);
        else return (int) ((amount - 100) * 2 + 50);
    }

}