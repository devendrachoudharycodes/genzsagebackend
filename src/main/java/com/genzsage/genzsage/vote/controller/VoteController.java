package com.genzsage.genzsage.vote.controller;

import com.genzsage.genzsage.vote.service.VoteService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/votes")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> castVote(@RequestBody VoteRequestDto voteRequest) {
        voteService.castVote(voteRequest.getAphorismId(), voteRequest.getValue());
        return ResponseEntity.ok().build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class VoteRequestDto {
        @NotNull
        private Long aphorismId;

        @NotNull
        private Double value;
    }
}
