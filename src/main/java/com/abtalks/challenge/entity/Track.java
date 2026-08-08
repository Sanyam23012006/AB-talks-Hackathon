package com.abtalks.challenge.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(length = 100, unique = true, nullable = false)
    private String slug;

    @Column(length = 255)
    private String tagline;

    @Column(nullable = false)
    private Integer totalDays = 60;
}
