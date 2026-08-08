package com.abtalks.challenge.dto;

import lombok.Builder;
import lombok.Data;
import com.abtalks.challenge.entity.Difficulty;

@Data
@Builder
public class DayDetailViewDto {
    private Integer dayNumber;
    private String title;
    private String summary;
    private Integer estimatedMins;
    private Difficulty difficulty;
    private String instructionsMarkdown;
    private String starterRepoUrl;
    private String conceptsTag;
    private boolean isSubmitted;
}
