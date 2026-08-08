package com.abtalks.challenge.dto;

import com.abtalks.challenge.entity.DailyTask;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class DashboardViewDto {
    private String studentName;
    private Integer currentStreak;
    private Integer longestStreak;
    private Integer totalCompleted;
    private Integer freezePasses;
    
    private DailyTask todayTask;
    private List<DotStatus> matrixStatusList;
    
    private boolean isNightOwlActive;
    private boolean isFirstDay;
    private boolean isMissedDay;

    public enum DotStatus {
        COMPLETED,
        MISSED,
        PENDING,
        ACTIVE
    }
}
