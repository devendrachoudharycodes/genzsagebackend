package com.genzsage.genzsage.comment.service;

import com.genzsage.genzsage.auth.AuthService;
import com.genzsage.genzsage.comment.entity.Comment;
import com.genzsage.genzsage.comment.entity.CommentVote;
import com.genzsage.genzsage.comment.repository.CommentRepository;
import com.genzsage.genzsage.comment.repository.CommentVoteRepository;
import com.genzsage.genzsage.sage.Sage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentVoteServiceImpl implements CommentVoteService {

    private final CommentVoteRepository commentVoteRepository;
    private final CommentRepository commentRepository;
    private final AuthService authService;

    @Override
    @Transactional
    public void vote(Long commentId, CommentVote.VoteType voteType) {
        Sage currentUser = authService.getCurrentUser();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        Optional<CommentVote> existingVoteOpt = commentVoteRepository.findBySageAndComment(currentUser, comment);

        if (existingVoteOpt.isPresent()) {
            CommentVote existingVote = existingVoteOpt.get();
            // User is changing their vote or removing it
            if (existingVote.getVoteType() == voteType) {
                // Same vote type clicked again, so remove the vote
                commentVoteRepository.delete(existingVote);
                if (voteType == CommentVote.VoteType.UPVOTE) {
                    comment.setUpvotes(comment.getUpvotes() - 1);
                } else {
                    comment.setDownvotes(comment.getDownvotes() - 1);
                }
            } else {
                // Different vote type, so change the vote
                existingVote.setVoteType(voteType);
                commentVoteRepository.save(existingVote);
                if (voteType == CommentVote.VoteType.UPVOTE) {
                    comment.setUpvotes(comment.getUpvotes() + 1);
                    comment.setDownvotes(comment.getDownvotes() - 1);
                } else {
                    comment.setUpvotes(comment.getUpvotes() - 1);
                    comment.setDownvotes(comment.getDownvotes() + 1);
                }
            }
        } else {
            // New vote
            CommentVote newVote = CommentVote.builder()
                    .sage(currentUser)
                    .comment(comment)
                    .voteType(voteType)
                    .build();
            commentVoteRepository.save(newVote);
            if (voteType == CommentVote.VoteType.UPVOTE) {
                comment.setUpvotes(comment.getUpvotes() + 1);
            } else {
                comment.setDownvotes(comment.getDownvotes() + 1);
            }
        }
        commentRepository.save(comment);
    }
}
