package com.genzsage.genzsage.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String text;
    private String authorUsername;
    private Instant createdAt;
    private Integer upvotes;
    private Integer downvotes;
    private Long parentId;
    private List<CommentDto> replies;
}
