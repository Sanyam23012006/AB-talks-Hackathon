package com.abtalks.challenge.service;

import com.abtalks.challenge.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StreakEngineTest {

    private StreakEngine streakEngine;

    @BeforeEach
    void setUp() {
        streakEngine = new StreakEngine();
    }

    @Test
    void testDay1Onboarding() {
        Student student = new Student();
        student.setLastSubmissionDate(null);
        student.setCurrentStreak(0);
        student.setLongestStreak(0);
        student.setTotalCompleted(0);

        LocalDateTime now = LocalDateTime.of(2023, 10, 15, 12, 0); // 12:00 PM
        streakEngine.updateStreak(student, now);

        assertEquals(1, student.getCurrentStreak());
        assertEquals(1, student.getLongestStreak());
        assertEquals(LocalDate.of(2023, 10, 15), student.getLastSubmissionDate());
    }

    @Test
    void testConsecutiveDaySubmission() {
        Student student = new Student();
        student.setLastSubmissionDate(LocalDate.of(2023, 10, 15));
        student.setCurrentStreak(1);
        student.setLongestStreak(1);
        student.setTotalCompleted(1);
        student.setFreezePasses(2);

        LocalDateTime now = LocalDateTime.of(2023, 10, 16, 15, 0); // Oct 16 3 PM
        streakEngine.updateStreak(student, now);

        assertEquals(2, student.getCurrentStreak());
        assertEquals(2, student.getLongestStreak());
        assertEquals(LocalDate.of(2023, 10, 16), student.getLastSubmissionDate());
    }

    @Test
    void testSameDaySubmission() {
        Student student = new Student();
        student.setLastSubmissionDate(LocalDate.of(2023, 10, 15));
        student.setCurrentStreak(3);
        student.setLongestStreak(5);
        student.setTotalCompleted(5);

        LocalDateTime now = LocalDateTime.of(2023, 10, 15, 20, 0); // Same day 8 PM
        streakEngine.updateStreak(student, now);

        assertEquals(3, student.getCurrentStreak()); // Remains unchanged
        assertEquals(LocalDate.of(2023, 10, 15), student.getLastSubmissionDate());
    }

    @Test
    void testTwoDayGapWithFreezePass() {
        Student student = new Student();
        student.setLastSubmissionDate(LocalDate.of(2023, 10, 14));
        student.setCurrentStreak(5);
        student.setLongestStreak(5);
        student.setTotalCompleted(10);
        student.setFreezePasses(2);

        LocalDateTime now = LocalDateTime.of(2023, 10, 16, 12, 0); // Gap of 1 day (15th missed)
        streakEngine.updateStreak(student, now);

        assertEquals(1, student.getFreezePasses()); // Consumed
        assertEquals(6, student.getCurrentStreak()); // Preserved and incremented
        assertEquals(LocalDate.of(2023, 10, 16), student.getLastSubmissionDate());
    }

    @Test
    void testTwoAmIstCutoff() {
        Student student = new Student();
        student.setLastSubmissionDate(LocalDate.of(2023, 10, 14));
        student.setCurrentStreak(2);
        student.setLongestStreak(2);
        student.setTotalCompleted(2);

        // Submitted at 1:30 AM on Oct 16. Should count as Oct 15 logical date.
        LocalDateTime now = LocalDateTime.of(2023, 10, 16, 1, 30);
        streakEngine.updateStreak(student, now);

        assertEquals(3, student.getCurrentStreak()); // Consecutive from 14th to 15th (logical)
        assertEquals(LocalDate.of(2023, 10, 15), student.getLastSubmissionDate());
    }
}
