package com.abtalks.challenge.repository;

import com.abtalks.challenge.entity.Submission;
import com.abtalks.challenge.entity.Student;
import com.abtalks.challenge.entity.DailyTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    Optional<Submission> findByStudentAndTask(Student student, DailyTask task);
}
