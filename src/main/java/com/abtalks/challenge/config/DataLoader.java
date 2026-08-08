package com.abtalks.challenge.config;

import com.abtalks.challenge.entity.DailyTask;
import com.abtalks.challenge.entity.Difficulty;
import com.abtalks.challenge.entity.Student;
import com.abtalks.challenge.entity.Track;
import com.abtalks.challenge.repository.DailyTaskRepository;
import com.abtalks.challenge.repository.StudentRepository;
import com.abtalks.challenge.repository.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final TrackRepository trackRepository;
    private final StudentRepository studentRepository;
    private final DailyTaskRepository dailyTaskRepository;

    @Override
    public void run(String... args) throws Exception {
        // Ensure idempotency
        if (trackRepository.count() > 0) {
            return;
        }

        // 1. Seed 1 Track
        Track track = new Track();
        track.setName("Full-Stack Java & Cloud Engineering");
        track.setSlug("fullstack-java");
        track.setTagline("Build high-performance REST APIs, Hibernate architectures & cloud deployments");
        track.setTotalDays(60);
        track = trackRepository.save(track);

        // 2. Seed 1 Mock Student
        Student student = new Student();
        student.setFullName("Aarav Sharma");
        student.setEmail("aarav.code@abesec.ac.in");
        student.setCollege("ABESEC Ghaziabad");
        student.setAvatarUrl("https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=100");
        student.setTrack(track);
        student.setCurrentStreak(12);
        student.setLongestStreak(12);
        student.setTotalCompleted(12);
        student.setFreezePasses(2);
        student.setLastSubmissionDate(LocalDate.now().minusDays(1)); // Yesterday
        studentRepository.save(student);

        // 3. Seed 60 DailyTask records
        List<DailyTask> tasks = new ArrayList<>();
        
        String[] titles = {
            "Git Workflows & Clean Architecture", "Introduction to Spring Boot & DI", "Controllers and Request Mapping", "Validations & Global Exception Handling", "Introduction to JPA and Hibernate",
            "Designing the Entity Layer & Enums", "Querying Data with JpaRepository", "Custom Queries with @Query & JPQL", "Database Migrations with Flyway", "One-to-One Relationships in JPA",
            "Many-to-One Relationships in JPA", "RESTful Endpoints & JPA Relationships", "Many-to-Many Relationships & Join Tables", "Testing Repositories with @DataJpaTest", "Unit Testing Services with Mockito",
            "Integration Testing with Testcontainers", "DTO Pattern & MapStruct", "Pagination & Sorting in Spring Data", "Spring Security Fundamentals", "Basic Auth vs JWT Authentication",
            "Implementing JWT Token Generation", "Securing Endpoints with Roles", "OAuth2 Login Integration", "REST API Documentation with Swagger", "Caching Strategies with Spring Cache",
            "Redis Integration for Distributed Caching", "Asynchronous Processing with @Async", "Scheduled Tasks with @Scheduled", "Sending Emails with JavaMailSender", "File Uploads & S3 Integration",
            "Introduction to Message Brokers", "RabbitMQ & AMQP Basics", "Kafka Producers and Consumers", "Event-Driven Architecture Concepts", "Implementing WebSockets with Spring",
            "Server-Sent Events (SSE)", "GraphQL in Spring Boot", "Advanced Hibernate: N+1 Problem", "Optimistic and Pessimistic Locking", "Soft Deletes & Auditing",
            "Designing Microservices with Spring Cloud", "Service Discovery with Eureka", "API Gateway & Routing", "Circuit Breakers with Resilience4j", "Distributed Tracing with Zipkin",
            "Dockerizing a Spring Boot Application", "Docker Compose for Local Environments", "CI/CD Pipelines with GitHub Actions", "Deploying to AWS ECS / Fargate", "Kubernetes Basics for Java Devs",
            "Helm Charts & Spring Boot", "Reactive Programming with Project Reactor", "Building Reactive APIs with Spring WebFlux", "R2DBC for Reactive Relational DBs", "Performance Tuning & Profiling",
            "Log Aggregation with ELK Stack", "Monitoring with Prometheus & Grafana", "Advanced Java: Records & Virtual Threads", "Spring Boot 3 & Native Images (GraalVM)", "Final Capstone Project Deployment"
        };

        for (int i = 1; i <= 60; i++) {
            DailyTask task = new DailyTask();
            task.setTrack(track);
            task.setDayNumber(i);
            
            if (i == 1) {
                task.setTitle(titles[0]);
                task.setDifficulty(Difficulty.BEGINNER);
                task.setEstimatedMins(45);
                task.setSummary("Master the basics of Git version control and understand clean architecture principles.");
                task.setConceptsTag("Git, Clean Architecture");
                task.setInstructionsMarkdown("## Objectives\n1. Setup Git\n2. Create a basic project structure\n3. Push to GitHub.");
            } else if (i == 12) {
                task.setTitle(titles[11]);
                task.setDifficulty(Difficulty.INTERMEDIATE);
                task.setEstimatedMins(45);
                task.setSummary("Design bidirectional @OneToMany associations with Hibernate and build paginated DTO endpoints.");
                task.setStarterRepoUrl("https://github.com/abtalks-challenge/day-12-starter");
                task.setConceptsTag("Java, Spring Boot, Hibernate, DTO Pattern");
                task.setInstructionsMarkdown("## Day 12 Specifications\n\n### Requirements:\n- Map `User` to `Posts` using bidirectional `@OneToMany`.\n- Create an endpoint `GET /api/users/{id}/posts` applying Spring Data Pagination (`Pageable`).\n- Return DTOs to avoid circular dependencies during JSON serialization.\n\n### Acceptance Criteria:\n1. The `@OneToMany` side must specify the `mappedBy` property.\n2. Response object must include metadata such as `totalPages` and `totalElements`.\n3. Verify performance by avoiding the N+1 problem on related collections.");
            } else {
                task.setTitle(titles[i - 1]);
                task.setDifficulty(i < 15 ? Difficulty.BEGINNER : (i < 40 ? Difficulty.INTERMEDIATE : Difficulty.ADVANCED));
                task.setEstimatedMins(45 + (i % 3) * 15);
                task.setSummary("Master " + titles[i - 1] + " and seamlessly integrate it into your robust architecture.");
                task.setConceptsTag("Backend, Java");
                task.setInstructionsMarkdown("## General Instructions\nComplete the assignment for " + titles[i - 1] + ". Explore external documentation where necessary.");
            }
            tasks.add(task);
        }
        
        dailyTaskRepository.saveAll(tasks);
    }
}
