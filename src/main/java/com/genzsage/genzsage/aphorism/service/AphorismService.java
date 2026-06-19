package com.genzsage.genzsage.aphorism.service;

import com.genzsage.genzsage.aphorism.dto.AphorismFeedDto;
import com.genzsage.genzsage.aphorism.dto.AphorismResponseDto;
import com.genzsage.genzsage.aphorism.dto.CreateAphorismRequestDto;

import java.util.List;

public interface AphorismService {
    AphorismResponseDto createAphorism(CreateAphorismRequestDto requestDto);
    List<AphorismFeedDto> getAllAphorismsForFeed();
}
