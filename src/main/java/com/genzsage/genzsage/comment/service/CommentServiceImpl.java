package com.genzsage.genzsage.comment.service;

import com.genzsage.genzsage.aphorism.entity.Aphorism;
import com.genzsage.genzsage.aphorism.repository.AphorismRepository;
import com.genzsage.genzsage.auth.AuthService;
import com.genzsage.genzsage.comment.dto.CommentDto;
import com.genzsage.genzsage.comment.dto.CreateCommentRequestDto;
import com.genzsage.genzsage.comment.entity.Comment;
import com.genzsage.genzsage.comment.repository.CommentRepository;
import com.genzsage.genzsage.sage.Sage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AphorismRepository aphorismRepository;
    private final AuthService authService;

    @Override
    @Transactional
    public CommentDto createComment(CreateCommentRequestDto request) {
        Sage currentUser = authService.getCurrentUser();
        Aphorism aphorism = aphorismRepository.findById(request.getAphorismId())
                .orElseThrow(() -> new EntityNotFoundException("Aphorism not found"));

        Comment parentComment = null;
        if (request.getParentCommentId() != null) {
            parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new EntityNotFoundException("Parent comment not found"));
        }

        Comment newComment = Comment.builder()
                .text(request.getText())
                .author(currentUser)
                .aphorism(aphorism)
                .parentComment(parentComment)
                .build();

        Comment savedComment = commentRepository.save(newComment);
        return mapToCommentDto(savedComment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsForAphorism(Long aphorismId) {
        List<Comment> topLevelComments = commentRepository.findTopLevelCommentsByAphorismId(aphorismId);
        return topLevelComments.stream()
                .map(this::mapToCommentDto)
                .collect(Collectors.toList());
    }

    private CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorUsername(comment.getAuthor().getIdentity())
                .createdAt(comment.getCreatedAt())
                .upvotes(comment.getUpvotes())
                .downvotes(comment.getDownvotes())
                .parentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null)
                .replies(comment.getReplies().stream()
                        .map(this::mapToCommentDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
