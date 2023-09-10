package com.decagon.scorecardapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeeklyScore extends BaseClass {
    private double algorithmScore;
    private double weeklyAssessment;
    private double qaTest;
    private double agileTest;
    private double weeklyTask;
    private double cumulativeScore;
    private String week;
    @ManyToOne
    @JoinColumn(name ="decadev_id", referencedColumnName = "id")
    @JsonBackReference
    private Decadev decadev;


}
