package com.abtalks.challenge.service;

import com.abtalks.challenge.entity.Student;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class StreakEngine {

    /**
     * Determines the logical submission date applying the 2:00 AM IST Grace Window.
     * Assumes submittedAt is in Asia/Kolkata timezone.
     */
    public LocalDate getLogicalSubmissionDate(LocalDateTime submittedAt) {
        if (submittedAt.getHour() < 2) {
            return submittedAt.toLocalDate().minusDays(1);
        }
        return submittedAt.toLocalDate();
    }

    public void updateStreak(Student student, LocalDateTime submittedAt) {
        LocalDate logicalSubmitDate = getLogicalSubmissionDate(submittedAt);
        LocalDate lastSubmitDate = student.getLastSubmissionDate();

        if (lastSubmitDate == null) {
            student.setCurrentStreak(1);
        } else {
            long daysBetween = ChronoUnit.DAYS.between(lastSubmitDate, logicalSubmitDate);

            if (daysBetween == 1) {
                student.setCurrentStreak(student.getCurrentStreak() + 1);
            } else if (daysBetween == 0) {
                // Same logical day, streak remains unchanged
            } else if (daysBetween == 2 && student.getFreezePasses() != null && student.getFreezePasses() > 0) {
                student.setFreezePasses(student.getFreezePasses() - 1);
                student.setCurrentStreak(student.getCurrentStreak() + 1);
            } else if (daysBetween > 0) { // Gap > 2 days or 2 days gap with no freeze passes
                student.setCurrentStreak(1);
            }
        }

        if (student.getCurrentStreak() > student.getLongestStreak()) {
            student.setLongestStreak(student.getCurrentStreak());
        }
        
        if (lastSubmitDate == null || logicalSubmitDate.isAfter(lastSubmitDate)) {
             student.setLastSubmissionDate(logicalSubmitDate);
        }
        
        student.setTotalCompleted(student.getTotalCompleted() + 1);
    }
}
