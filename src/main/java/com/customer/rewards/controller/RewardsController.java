package com.customer.rewards.controller;

import com.customer.rewards.model.Transaction;
import com.customer.rewards.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rewards")
public class RewardsController
{

    @Autowired
    private RewardsService rewardsService;

    @PostMapping
    public Map<String, Map<String,Integer>> getRewards(@RequestBody List<Transaction> transactions)
    {
        return  rewardsService.computeRewards(transactions);
    }
}
