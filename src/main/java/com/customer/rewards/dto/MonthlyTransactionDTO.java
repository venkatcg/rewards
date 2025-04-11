package com.customer.rewards.dto;

import lombok.Data;

import java.util.List;

@Data
public class MonthlyTransactionDTO {
    private String customerId;
    private String month;
    private List<TransactionDTO> transactions;
}