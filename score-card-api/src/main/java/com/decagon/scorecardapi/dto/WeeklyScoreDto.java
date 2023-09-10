package com.decagon.scorecardapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyScoreDto {

    private double algorithmScore;
    private double weeklyAssessment;
    private double qaTest;
    private double agileTest;
    private double weeklyTask;
    private String week;

}
