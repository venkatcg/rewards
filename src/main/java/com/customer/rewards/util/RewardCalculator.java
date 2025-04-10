package com.customer.rewards.util;

public class RewardCalculator {
    public int calculatePoints(double amount) {
        if (amount <= 50) return 0;
        if (amount <= 100) return (int) (amount - 50);
        return (int) ((amount - 100) * 2 + 50);
    }
}
