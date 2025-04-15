package com.customer.rewards.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class CustomerMonthlyPointsDTO {
    private String customerId;
    private String month;
    private int points;

    public CustomerMonthlyPointsDTO(String customerId, String customerName, String month, int points) {
        this.customerId = customerId;
        this.month = month;
        this.points = points;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public void setMonth(String month) {
        this.month = month;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
