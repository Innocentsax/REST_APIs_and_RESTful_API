package com.decagon.scorecardapi.repository;

import com.decagon.scorecardapi.model.Decadev;
import com.decagon.scorecardapi.model.WeeklyScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyScoreRepository extends JpaRepository<WeeklyScore,Long> {
    WeeklyScore findWeeklyScoreByWeekAndDecadev(String week, Decadev decadev);
    List<WeeklyScore> findWeeklyScoresByDecadev(Decadev decadev);
}
