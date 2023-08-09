package dev.InnocentUdo.QuizApp.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "Quiz")
@Data
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    @ManyToMany
    private List<Question> question;
}
