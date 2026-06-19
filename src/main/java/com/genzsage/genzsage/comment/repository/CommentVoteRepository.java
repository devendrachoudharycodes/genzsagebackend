package com.genzsage.genzsage.comment.repository;

import com.genzsage.genzsage.comment.entity.Comment;
import com.genzsage.genzsage.comment.entity.CommentVote;
import com.genzsage.genzsage.sage.Sage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentVoteRepository extends JpaRepository<CommentVote, Long> {
    Optional<CommentVote> findBySageAndComment(Sage sage, Comment comment);
}
