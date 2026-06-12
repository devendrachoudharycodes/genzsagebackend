package com.genzsage.genzsage.question.service;

import com.genzsage.genzsage.question.entity.Question;
import com.genzsage.genzsage.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Optional<Question> getQuestionById(Long id) {
        return questionRepository.findByIdAndDeletedFalse(id);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAllByDeletedFalse();
    }

    @Override
    public Question updateQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.findById(id).ifPresent(question -> {
            question.setDeleted(true);
            questionRepository.save(question);
        });
    }
}
