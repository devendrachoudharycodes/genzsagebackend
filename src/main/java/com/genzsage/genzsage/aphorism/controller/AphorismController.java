package com.genzsage.genzsage.aphorism.controller;

import com.genzsage.genzsage.aphorism.dto.AphorismResponseDto;
import com.genzsage.genzsage.aphorism.dto.AphorismSummaryDto;
import com.genzsage.genzsage.aphorism.dto.CreateAphorismRequestDto;
import com.genzsage.genzsage.aphorism.service.AphorismService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    private final AphorismService aphorismService;

    @PostMapping
    public ResponseEntity<AphorismResponseDto> createAphorism(@Valid @RequestBody CreateAphorismRequestDto requestDto) {
        AphorismResponseDto responseDto = aphorismService.createAphorism(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AphorismSummaryDto>> getAllAphorisms() {
        List<AphorismSummaryDto> aphorisms = aphorismService.getAllAphorisms();
        return ResponseEntity.ok(aphorisms);
    }
}
