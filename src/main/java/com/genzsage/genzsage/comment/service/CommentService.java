package com.genzsage.genzsage.comment.service;

import com.genzsage.genzsage.comment.dto.CommentDto;
import com.genzsage.genzsage.comment.dto.CreateCommentRequestDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CreateCommentRequestDto request);
    List<CommentDto> getCommentsForAphorism(Long aphorismId);
}
