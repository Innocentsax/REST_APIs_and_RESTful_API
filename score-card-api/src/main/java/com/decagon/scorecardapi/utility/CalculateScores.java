package com.decagon.scorecardapi.utility;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CalculateScores {


    public static Double weeklyCumulative(double algorithm, double weeklyAssessment, double qaTest, double agileTest, double weeklyTask) {
        return ((algorithm * 0.15) + (weeklyAssessment * 0.15) + (qaTest * 0.1) + (agileTest * 0.2) + (weeklyTask * 0.4));
    }
}
