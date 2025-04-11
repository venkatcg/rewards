package com.customer.rewards.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class RewardResponse {
    private String customerId;
    private String customerName;

    private Map<String, Integer> monthlyPoints;
    private int totalPoints;

    // Getters and Setters
}