package com.genzsage.genzsage.question.repository;

import com.genzsage.genzsage.question.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findByIdAndDeletedFalse(Long id);
    List<Question> findAllByDeletedFalse();
}
