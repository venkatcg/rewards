package com.customer.rewards.controller;

import com.customer.rewards.dto.CustomerRewardDTO;
import com.customer.rewards.dto.DateRangeRewardsDTO;
import com.customer.rewards.dto.MonthlyTransactionDTO;
import com.customer.rewards.service.RewardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rewards")
public class RewardController {
    private final RewardService service;
    public RewardController(RewardService service) { this.service = service; }

    @GetMapping("/{customerId}")
    public CustomerRewardDTO getRewards(@PathVariable String customerId) {
        return service.getCustomerRewards(customerId);
    }

    @GetMapping("/{customerId}/month/{ym}")
    public MonthlyTransactionDTO getMonth(@PathVariable String customerId, @PathVariable String ym) {
        return service.getMonthlyTransactions(customerId, ym);
    }

    @GetMapping("/range")
    public DateRangeRewardsDTO getRange(@RequestParam String from, @RequestParam String to) {
        return service.getRewardsInRange(from, to);
    }
}