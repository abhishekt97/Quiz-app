package com.java.springboot.quizapp.service;

import com.java.springboot.quizapp.dao.QuestionDAO;
import com.java.springboot.quizapp.dao.QuizDAO;
import com.java.springboot.quizapp.model.Question;
import com.java.springboot.quizapp.model.QuestionWrapper;
import com.java.springboot.quizapp.model.Quiz;
import com.java.springboot.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    QuizDAO quizDAO;

    QuestionDAO questionDAO;

    @Autowired
    public QuizService(QuizDAO quizDAO, QuestionDAO questionDAO){
        this.quizDAO=quizDAO;
        this.questionDAO=questionDAO;
    }

    public ResponseEntity<String> createQuiz(String category, int noOfQues, String title) {

        try{
            List<Question> questions = questionDAO.findRandomQuestionsByCategory(category, noOfQues);

            Quiz quiz = new Quiz();

            quiz.setTitle(title);
            quiz.setQuestions(questions);
            quizDAO.save(quiz);

            return new ResponseEntity<>(" success", HttpStatus.CREATED);

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {
        try{
            Optional<Quiz> quiz = quizDAO.findById(id);
            List<Question> questionsFromDB =quiz.get().getQuestions();
            List<QuestionWrapper> questionForUser = new ArrayList<>();

            for(Question question : questionsFromDB){
                QuestionWrapper questionWrapper =
                        new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
                questionForUser.add(questionWrapper);
            }
            return new ResponseEntity<>(questionForUser, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Integer> calculateResult(int id, List<Response> response) {
        try{
            Quiz quiz = quizDAO.findById(id).get();
            List<Question> questionList = quiz.getQuestions();
            int right=0;
            int i=0;
            for( Response response1: response){
                if(response1.getResponse().equals(questionList.get(i).getRightAnswer())){
                    right++;
                }
                i++;
            }
            return new ResponseEntity<>(right, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
    }
}
