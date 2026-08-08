package com.abtalks.challenge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"track_id", "dayNumber"}))
public class DailyTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "track_id", nullable = false)
    private Track track;

    @Column(nullable = false)
    private Integer dayNumber;

    @Column(length = 200, nullable = false)
    private String title;

    @Column(length = 350)
    private String summary;

    private Integer estimatedMins;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Column(columnDefinition = "LONGTEXT")
    private String instructionsMarkdown;

    @Column(length = 350)
    private String starterRepoUrl;

    @Column(length = 255)
    private String conceptsTag;
}
