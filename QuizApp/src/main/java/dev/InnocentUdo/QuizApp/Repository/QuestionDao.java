package dev.InnocentUdo.QuizApp.Repository;

import dev.InnocentUdo.QuizApp.Entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "SELECT q FROM Question q WHERE q.category = :category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    //@Query(value = "SELECT q FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT : numQ", nativeQuery = true)
    List<Question> findRandomQuestionByCategory(@Param("category") String category, @Param("numQ") int numQ);
}
