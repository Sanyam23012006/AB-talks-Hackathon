package com.abtalks.challenge.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SubmissionRequest {
    @NotNull(message = "Day number is required")
    @Min(value = 1, message = "Day number must be at least 1")
    @Max(value = 60, message = "Day number must not exceed 60")
    private Integer dayNumber;

    @NotNull(message = "GitHub URL is required")
    @Pattern(regexp = "^https?:\\/\\/(www\\.)?github\\.com\\/[A-Za-z0-9_.-]+\\/[A-Za-z0-9_.-]+.*$", message = "Must be a valid GitHub repository or commit URL")
    private String githubUrl;

    @NotNull(message = "LinkedIn URL is required")
    @Pattern(regexp = "^https?:\\/\\/(www\\.)?linkedin\\.com\\/(posts|feed|in)\\/.*$", message = "Must be a valid LinkedIn post URL")
    private String linkedinUrl;

    private String notes;
}
