package com.abtalks.challenge.controller;

import com.abtalks.challenge.dto.DashboardViewDto;
import com.abtalks.challenge.entity.DailyTask;
import com.abtalks.challenge.entity.Difficulty;
import com.abtalks.challenge.service.ChallengeService;
import com.abtalks.challenge.service.DashboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ChallengeViewController.class)
class ChallengeViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DashboardService dashboardService;

    @MockBean
    private ChallengeService challengeService;

    @Test
    void testLandingPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("landing"));
    }

    @Test
    void testDashboardPage() throws Exception {
        DashboardViewDto mockDto = DashboardViewDto.builder()
                .studentName("Aarav")
                .currentStreak(1)
                .matrixStatusList(Collections.emptyList())
                .build();
        
        when(dashboardService.getDashboard(anyLong(), anyInt())).thenReturn(mockDto);

        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("dashboard"));
    }

    @Test
    void testDayDetailPage() throws Exception {
        DailyTask mockTask = new DailyTask();
        mockTask.setDayNumber(12);
        mockTask.setTitle("Test Task");
        mockTask.setDifficulty(Difficulty.INTERMEDIATE);
        
        when(challengeService.getTaskSpecification(anyLong(), anyInt())).thenReturn(mockTask);

        mockMvc.perform(get("/day/12"))
                .andExpect(status().isOk())
                .andExpect(view().name("day-detail"));
    }
}
