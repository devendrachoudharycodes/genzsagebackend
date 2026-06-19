package com.genzsage.genzsage.aphorism.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AphorismFeedDto {

    private String title;
    private Instant created;
    private SageDetailsDto sage;
    private QuestionDetailsDto question;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SageDetailsDto {
        private String identity;
        private String profileName;
        private String profilePicUrl;
        private String country;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionDetailsDto {
        private String question;
        private String optionOne;
        private String optionTwo;
        private String optionThree;
        private String optionFour;
        private Integer correctAnswer;
        private String explanation;
        private String aiAnswer;
    }
}
