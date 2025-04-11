package com.customer.rewards.dto;

import lombok.Data;

import java.util.List;

@Data
public class DateRangeRewardsDTO {
    private String from;
    private String to;
    private List<CustomerMonthlyPointsDTO> rewards;
}
