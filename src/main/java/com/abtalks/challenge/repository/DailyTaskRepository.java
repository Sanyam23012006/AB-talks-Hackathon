package com.abtalks.challenge.repository;

import com.abtalks.challenge.entity.DailyTask;
import com.abtalks.challenge.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DailyTaskRepository extends JpaRepository<DailyTask, Long> {
    Optional<DailyTask> findByTrackAndDayNumber(Track track, Integer dayNumber);
}
