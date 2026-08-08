package com.abtalks.challenge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String fullName;

    @Column(length = 150, unique = true, nullable = false)
    private String email;

    @Column(length = 200)
    private String college;

    @Column(length = 255)
    private String avatarUrl;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;

    private Integer currentStreak = 0;
    private Integer longestStreak = 0;
    private Integer totalCompleted = 0;
    private Integer freezePasses = 2;

    private LocalDate lastSubmissionDate;
}
