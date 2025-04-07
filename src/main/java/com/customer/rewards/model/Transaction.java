package com.customer.rewards.model;

import java.time.LocalDate;

public class Transaction {

    private String customer;
    private double amount;

    public Transaction(String customer, double amount, LocalDate date) {
        this.customer = customer;
        this.amount = amount;
        this.date = date;
    }

    private LocalDate date;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
