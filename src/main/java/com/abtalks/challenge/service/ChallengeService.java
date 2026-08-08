package com.abtalks.challenge.service;

import com.abtalks.challenge.entity.Student;
import com.abtalks.challenge.entity.DailyTask;
import com.abtalks.challenge.entity.Submission;
import com.abtalks.challenge.repository.StudentRepository;
import com.abtalks.challenge.repository.DailyTaskRepository;
import com.abtalks.challenge.repository.SubmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final StudentRepository studentRepository;
    private final DailyTaskRepository dailyTaskRepository;
    private final SubmissionRepository submissionRepository;
    private final StreakEngine streakEngine;

    public DailyTask getTaskSpecification(Long trackId, int dayNumber) {
        return dailyTaskRepository.findAll().stream()
            .filter(task -> task.getTrack().getId().equals(trackId) && task.getDayNumber() == dayNumber)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Transactional
    public Submission submitChallenge(Long studentId, Long taskId, String githubUrl, String linkedinUrl, String notes) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
            
        DailyTask task = dailyTaskRepository.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));
            
        if (submissionRepository.findByStudentAndTask(student, task).isPresent()) {
            throw new RuntimeException("Challenge already submitted for this task.");
        }

        Submission submission = new Submission();
        submission.setStudent(student);
        submission.setTask(task);
        submission.setDayNumber(task.getDayNumber());
        submission.setGithubUrl(githubUrl);
        submission.setLinkedinUrl(linkedinUrl);
        submission.setNotes(notes);
        
        LocalDateTime now = LocalDateTime.now();
        submission.setSubmittedAt(now);
        submission.setIsLate(false); 
        
        streakEngine.updateStreak(student, now);
        studentRepository.save(student);
        
        return submissionRepository.save(submission);
    }
}
