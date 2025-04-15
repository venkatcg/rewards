package com.customer.rewards.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class DateRangeRewardsDTO123 {

    // Getters and Setters
    private String customerId;
    private String from; // e.g. "2025-03"

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getTotalRewards() {
        return totalRewards;
    }

    public void setTotalRewards(int totalRewards) {
        this.totalRewards = totalRewards;
    }

    public List<CustomerMonthlyPointsDTO> getMonthlyRewards() {
        return monthlyRewards;
    }

    public void setMonthlyRewards(List<CustomerMonthlyPointsDTO> monthlyRewards) {
        this.monthlyRewards = monthlyRewards;
    }

    private String to; // e.g. "2025-05"
    private int totalRewards;
    private List<CustomerMonthlyPointsDTO> monthlyRewards;

    // Constructor
    public DateRangeRewardsDTO123(String customerId, String from, String to, int totalRewards, List<CustomerMonthlyPointsDTO> monthlyRewards) {
        this.customerId = customerId;
        this.from = from;
        this.to = to;
        this.totalRewards = totalRewards;
        this.monthlyRewards = monthlyRewards;
    }

}
