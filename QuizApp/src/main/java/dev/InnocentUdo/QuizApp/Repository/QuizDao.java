package dev.InnocentUdo.QuizApp.Repository;

import dev.InnocentUdo.QuizApp.Entities.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao extends JpaRepository<Quiz, Integer> {
}
