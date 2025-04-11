package com.customer.rewards.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CustomerRewardDTO {
    private String customerId;
    private String customerName;
    private Map<String, Integer> monthlyPoints;
    private int totalPoints;
}



