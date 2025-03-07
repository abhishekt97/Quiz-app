package com.java.springboot.quizapp.dao;

import com.java.springboot.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDAO extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query(value = "Select * From question q Where q.category=:category Order by RANDOM() Limit :noOfQues" , nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int noOfQues);
}
