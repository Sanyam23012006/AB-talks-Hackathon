package com.abtalks.challenge.service;

import com.abtalks.challenge.dto.DashboardViewDto;
import com.abtalks.challenge.entity.Student;
import com.abtalks.challenge.entity.DailyTask;
import com.abtalks.challenge.entity.Submission;
import com.abtalks.challenge.repository.StudentRepository;
import com.abtalks.challenge.repository.DailyTaskRepository;
import com.abtalks.challenge.repository.SubmissionRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final StudentRepository studentRepository;
    private final DailyTaskRepository dailyTaskRepository;
    private final SubmissionRepository submissionRepository;

    public DashboardViewDto getDashboard(Long studentId, int currentDayNumber) {
        Student student = studentRepository.findById(studentId)
            .orElseThrow(() -> new RuntimeException("Student not found"));
            
        DailyTask todayTask = dailyTaskRepository
            .findByTrackAndDayNumber(student.getTrack(), currentDayNumber)
            .orElse(null);
            
        List<Submission> allSubmissions = submissionRepository.findAll();
        Set<Integer> completedDays = allSubmissions.stream()
            .filter(s -> s.getStudent().getId().equals(studentId))
            .map(Submission::getDayNumber)
            .collect(Collectors.toSet());

        List<DashboardViewDto.DotStatus> statusList = new ArrayList<>();
        int totalDays = student.getTrack() != null && student.getTrack().getTotalDays() != null 
                        ? student.getTrack().getTotalDays() : 60;
        
        for (int i = 1; i <= totalDays; i++) {
            if (completedDays.contains(i)) {
                statusList.add(DashboardViewDto.DotStatus.COMPLETED);
            } else if (i == currentDayNumber) {
                statusList.add(DashboardViewDto.DotStatus.ACTIVE);
            } else if (i < currentDayNumber) {
                statusList.add(DashboardViewDto.DotStatus.MISSED);
            } else {
                statusList.add(DashboardViewDto.DotStatus.PENDING);
            }
        }
        
        LocalDateTime istNow = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        boolean isNightOwl = istNow.getHour() < 2;

        return DashboardViewDto.builder()
                .studentName(student.getFullName())
                .currentStreak(student.getCurrentStreak())
                .longestStreak(student.getLongestStreak())
                .totalCompleted(student.getTotalCompleted())
                .freezePasses(student.getFreezePasses())
                .todayTask(todayTask)
                .matrixStatusList(statusList)
                .isNightOwlActive(isNightOwl)
                .isFirstDay(student.getCurrentStreak() == 0)
                .isMissedDay(student.getCurrentStreak() == 0 && completedDays.size() > 0)
                .build();
    }
}
