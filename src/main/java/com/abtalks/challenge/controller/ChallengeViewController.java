package com.abtalks.challenge.controller;

import com.abtalks.challenge.dto.DashboardViewDto;
import com.abtalks.challenge.dto.DayDetailViewDto;
import com.abtalks.challenge.entity.DailyTask;
import com.abtalks.challenge.service.ChallengeService;
import com.abtalks.challenge.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChallengeViewController {

    private final DashboardService dashboardService;
    private final ChallengeService challengeService;

    @GetMapping("/")
    public String landing(Model model) {
        model.addAttribute("totalStudents", "2,480+");
        model.addAttribute("totalCommits", "48k+");
        model.addAttribute("placed", "312+");
        return "landing";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Hardcoded studentId=1 for demo
        DashboardViewDto dto = dashboardService.getDashboard(1L, 12);
        model.addAttribute("dashboard", dto);
        return "dashboard";
    }

    @GetMapping("/day/{dayNumber}")
    public String dayDetail(@PathVariable Integer dayNumber, Model model) {
        // Hardcoded trackId=1 for demo
        DailyTask task = challengeService.getTaskSpecification(1L, dayNumber);
        
        DayDetailViewDto dto = DayDetailViewDto.builder()
                .dayNumber(task.getDayNumber())
                .title(task.getTitle())
                .summary(task.getSummary())
                .estimatedMins(task.getEstimatedMins())
                .difficulty(task.getDifficulty())
                .instructionsMarkdown(task.getInstructionsMarkdown())
                .starterRepoUrl(task.getStarterRepoUrl())
                .conceptsTag(task.getConceptsTag())
                .isSubmitted(false) 
                .build();
                
        model.addAttribute("task", dto);
        return "day-detail";
    }
}
