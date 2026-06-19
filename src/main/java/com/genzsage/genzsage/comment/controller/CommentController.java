package com.genzsage.genzsage.comment.controller;

import com.genzsage.genzsage.comment.dto.CommentDto;
import com.genzsage.genzsage.comment.dto.CreateCommentRequestDto;
import com.genzsage.genzsage.comment.entity.CommentVote;
import com.genzsage.genzsage.comment.service.CommentService;
import com.genzsage.genzsage.comment.service.CommentVoteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentVoteService commentVoteService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CreateCommentRequestDto request) {
        CommentDto createdComment = commentService.createComment(request);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @GetMapping("/aphorism/{aphorismId}")
    public ResponseEntity<List<CommentDto>> getCommentsForAphorism(@PathVariable Long aphorismId) {
        List<CommentDto> comments = commentService.getCommentsForAphorism(aphorismId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{commentId}/vote")
    public ResponseEntity<Void> vote(@PathVariable Long commentId, @Valid @RequestBody VoteRequest request) {
        commentVoteService.vote(commentId, request.getVoteType());
        return ResponseEntity.ok().build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteRequest {
        @NotNull
        private CommentVote.VoteType voteType;
    }
}
