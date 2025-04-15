package com.customer.rewards;

import com.customer.rewards.controller.RewardController;
import com.customer.rewards.dto.CustomerMonthlyPointsDTO;
import com.customer.rewards.dto.CustomerRewardDTO;
import com.customer.rewards.dto.DateRangeRewardsDTO;
import com.customer.rewards.dto.MonthlyTransactionDTO;
import com.customer.rewards.service.RewardService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RewardController.class)
@AutoConfigureMockMvc
class RewardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RewardService rewardService;

    @Test
    void testGetCustomerRewards() throws Exception {
        // Arrange
        CustomerRewardDTO mockDto = new CustomerRewardDTO();
        mockDto.setCustomerId("C1");
        mockDto.setCustomerName("Alice");
        mockDto.setTotalPoints(100);
        mockDto.setMonthlyPoints(Map.of("JANUARY", 100));

        Mockito.when(rewardService.getCustomerRewards("C1")).thenReturn(mockDto);

        MvcResult result = mockMvc.perform(get("/api/rewards/C1")).andExpect(status().isOk()).andReturn();

        // Print the response content
        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }

    @Test
    void testGetMonthTransactionsByPathVariables() throws Exception {
        // Arrange
        String customerId = "C2";
        String yearMonth = "2024-03";
        MonthlyTransactionDTO mockDto = new MonthlyTransactionDTO();
        mockDto.setCustomerId(customerId);
        mockDto.setMonth(yearMonth);

        mockDto.setTransactions(Collections.emptyList());

        Mockito.when(rewardService.getMonthlyTransactions(eq(customerId), eq(yearMonth))).thenReturn(mockDto);


        MvcResult result = mockMvc.perform(get("/api/rewards/{customerId}/month/{ym}", customerId, yearMonth)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }

    @Test
    void testGetRewardsByDateRange() throws Exception {
        // Arrange
        String fromDate = "2024-01-01";
        String toDate = "2024-03-31";
        DateRangeRewardsDTO mockDto = new DateRangeRewardsDTO();
        mockDto.setFrom(fromDate);
        mockDto.setTo(toDate);
        // Create sample monthly points data
        CustomerMonthlyPointsDTO pointsJan = new CustomerMonthlyPointsDTO("ALL", null, "JANUARY", 150);
        CustomerMonthlyPointsDTO pointsFeb = new CustomerMonthlyPointsDTO("ALL", null, "FEBRUARY", 200);
        mockDto.setRewards(List.of(pointsJan, pointsFeb));

        Mockito.when(rewardService.getRewardsInRange(eq(fromDate), eq(toDate))).thenReturn(mockDto);
        MvcResult result = mockMvc.perform(get("/api/rewards/range")
                        .param("from", fromDate)
                        .param("to", toDate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);

    }
}
