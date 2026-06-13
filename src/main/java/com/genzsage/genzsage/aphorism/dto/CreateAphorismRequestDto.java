package com.genzsage.genzsage.aphorism.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAphorismRequestDto {

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 255, message = "Title cannot exceed 255 characters")
    private String title;

    @NotNull
    @Size(min = 1, message = "At least one tag is required")
    private Set<String> tags;

    @NotBlank(message = "Question text cannot be blank")
    @Size(max = 500, message = "Question text cannot exceed 500 characters")
    private String question;

    @NotBlank(message = "Option one cannot be blank")
    private String optionOne;

    @NotBlank(message = "Option two cannot be blank")
    private String optionTwo;

    @NotBlank(message = "Option three cannot be blank")
    private String optionThree;

    @NotBlank(message = "Option four cannot be blank")
    private String optionFour;

    @NotNull(message = "Correct answer cannot be null")
    @Min(value = 1, message = "Answer must be between 1 and 4")
    @Max(value = 4, message = "Answer must be between 1 and 4")
    private Integer correctAnswer;

    @Size(max = 2000, message = "Explanation cannot exceed 2000 characters")
    private String explanation;
}
