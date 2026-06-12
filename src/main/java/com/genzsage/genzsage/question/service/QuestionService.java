package com.genzsage.genzsage.question.service;

import com.genzsage.genzsage.question.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    Question createQuestion(Question question);
    Optional<Question> getQuestionById(Long id);
    List<Question> getAllQuestions();
    Question updateQuestion(Question question);
    void deleteQuestion(Long id);
}
