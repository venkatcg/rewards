package com.customer.rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerMonthlyPointsDTO {
    private String customerId;
    private String customerName;
    private String month;
    private int points;
}
