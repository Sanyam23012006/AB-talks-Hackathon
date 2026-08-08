package com.abtalks.challenge.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmissionResponse {
    private boolean success;
    private String message;
    private Integer newStreak;
    private Integer totalCompleted;
}
