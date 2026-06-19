package com.genzsage.genzsage.comment.service;

import com.genzsage.genzsage.comment.entity.CommentVote;

public interface CommentVoteService {
    void vote(Long commentId, CommentVote.VoteType voteType);
}
