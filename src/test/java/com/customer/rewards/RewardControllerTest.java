package com.customer.rewards;

import com.customer.rewards.controller.RewardController;
import com.customer.rewards.dto.CustomerRewardDTO;
import com.customer.rewards.repository.TransactionRepository;
import com.customer.rewards.service.RewardService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
}
