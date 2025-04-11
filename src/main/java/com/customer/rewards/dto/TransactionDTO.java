package com.customer.rewards.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDTO {
    private double amount;
    private LocalDate transactionDate;
    private int points;
}