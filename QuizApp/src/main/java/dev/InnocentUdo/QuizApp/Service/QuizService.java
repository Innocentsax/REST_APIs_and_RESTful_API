package dev.InnocentUdo.QuizApp.Service;

import dev.InnocentUdo.QuizApp.Entities.Question;
import dev.InnocentUdo.QuizApp.Entities.QuestionWrapper;
import dev.InnocentUdo.QuizApp.Entities.Quiz;
import dev.InnocentUdo.QuizApp.Entities.Response;
import dev.InnocentUdo.QuizApp.Repository.QuestionDao;
import dev.InnocentUdo.QuizApp.Repository.QuizDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    
    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestion(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestion();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();

        for(Question q : questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }

        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> question  = quiz.getQuestion();
        int right = 0;
        int i = 0;
        for(Response response : responses){
            if(response.getResponse().equals(question.get(i).getRightAnswer())){
                right++;
            }i++;
        }return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
