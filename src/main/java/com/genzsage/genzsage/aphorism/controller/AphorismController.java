package com.genzsage.genzsage.aphorism.controller;

import com.genzsage.genzsage.aphorism.dto.AphorismFeedDto;
import com.genzsage.genzsage.aphorism.dto.AphorismResponseDto;
import com.genzsage.genzsage.aphorism.dto.CreateAphorismRequestDto;
import com.genzsage.genzsage.aphorism.service.AphorismService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/aphorisms")
@RequiredArgsConstructor
public class AphorismController {

    private static final Logger log = LoggerFactory.getLogger(AphorismController.class);

    private final AphorismService aphorismService;

    @PostMapping
    public ResponseEntity<AphorismResponseDto> createAphorism(@Valid @RequestBody CreateAphorismRequestDto requestDto) {
        log.info("Received request to create aphorism: {}", requestDto);

        AphorismResponseDto responseDto = aphorismService.createAphorism(requestDto);

        log.info("Successfully created aphorism with ID: {}", responseDto.getId());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AphorismFeedDto>> getAllAphorismsForFeed() {
        log.info("Received request to get all aphorisms for feed");

        List<AphorismFeedDto> aphorisms = aphorismService.getAllAphorismsForFeed();

        log.info("Returning {} aphorisms for feed", aphorisms.size());
        return ResponseEntity.ok(aphorisms);
    }
}
