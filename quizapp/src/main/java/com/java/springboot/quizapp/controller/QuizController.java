package com.java.springboot.quizapp.controller;

import com.java.springboot.quizapp.model.QuestionWrapper;
import com.java.springboot.quizapp.model.Response;
import com.java.springboot.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {


    QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService){
        this.quizService=quizService;
    }

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int noOfQues,@RequestParam String title){
        return quizService.createQuiz(category, noOfQues, title);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable int id,@RequestBody List<Response> response){
        return quizService.calculateResult(id, response);
    }

}
