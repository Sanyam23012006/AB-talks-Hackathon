package com.abtalks.challenge.controller;

import com.abtalks.challenge.dto.SubmissionRequest;
import com.abtalks.challenge.dto.SubmissionResponse;
import com.abtalks.challenge.entity.DailyTask;
import com.abtalks.challenge.entity.Submission;
import com.abtalks.challenge.service.ChallengeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
public class SubmissionApiController {

    private final ChallengeService challengeService;

    @PostMapping
    public ResponseEntity<SubmissionResponse> submitTask(@Valid @RequestBody SubmissionRequest request) {
        Long hardcodedStudentId = 1L;
        Long trackId = 1L;
        
        DailyTask task = challengeService.getTaskSpecification(trackId, request.getDayNumber());
        
        Submission submission = challengeService.submitChallenge(
                hardcodedStudentId,
                task.getId(),
                request.getGithubUrl(),
                request.getLinkedinUrl(),
                request.getNotes()
        );

        SubmissionResponse response = SubmissionResponse.builder()
                .success(true)
                .message("Successfully submitted day " + request.getDayNumber() + " challenge!")
                .newStreak(submission.getStudent().getCurrentStreak())
                .totalCompleted(submission.getStudent().getTotalCompleted())
                .build();

        return ResponseEntity.ok(response);
    }
}
