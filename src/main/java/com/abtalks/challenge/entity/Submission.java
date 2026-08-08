package com.abtalks.challenge.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "task_id"}))
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private DailyTask task;

    @Column(nullable = false)
    private Integer dayNumber;

    @Pattern(regexp = "^https://(www\\.)?github\\.com/.+/.+/commit/.*$", message = "Must be a valid GitHub commit URL")
    @Column(length = 350)
    private String githubUrl;

    @Pattern(regexp = "^https://(www\\.)?linkedin\\.com/.*$", message = "Must be a valid LinkedIn post URL")
    @Column(length = 350)
    private String linkedinUrl;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private Boolean isLate;
    private LocalDateTime submittedAt;
}
