package com.genzsage.genzsage.aphorism.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AphorismSummaryDto {

    private long id;
    private String title;
    private Instant created;
    private String sageUsername;
}
