package com.genzsage.genzsage.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequestDto {
    @NotNull
    private Long aphorismId;

    private Long parentCommentId; // Can be null for top-level comments

    @NotBlank
    private String text;
}
