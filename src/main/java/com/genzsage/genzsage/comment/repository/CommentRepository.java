package com.genzsage.genzsage.comment.repository;

import com.genzsage.genzsage.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.aphorism.id = :aphorismId AND c.parentComment IS NULL")
    List<Comment> findTopLevelCommentsByAphorismId(Long aphorismId);
}
