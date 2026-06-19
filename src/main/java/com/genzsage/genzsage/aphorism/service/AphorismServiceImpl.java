package com.genzsage.genzsage.aphorism.service;

import com.genzsage.genzsage.aphorism.dto.AphorismFeedDto;
import com.genzsage.genzsage.aphorism.dto.AphorismResponseDto;
import com.genzsage.genzsage.aphorism.dto.CreateAphorismRequestDto;
import com.genzsage.genzsage.aphorism.entity.Aphorism;
import com.genzsage.genzsage.aphorism.repository.AphorismRepository;
import com.genzsage.genzsage.auth.AuthService;
import com.genzsage.genzsage.question.entity.Question;
import com.genzsage.genzsage.question.service.QuestionService;
import com.genzsage.genzsage.sage.Sage;
import com.genzsage.genzsage.tag.entity.Tag;
import com.genzsage.genzsage.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AphorismServiceImpl implements AphorismService {

    private final AphorismRepository aphorismRepository;
    private final QuestionService questionService;
    private final TagService tagService;
    private final AuthService authService;

    @Override
    @Transactional
    public AphorismResponseDto createAphorism(CreateAphorismRequestDto requestDto) {
        Sage sage = authService.getCurrentUser();

        // 1. Create the Question by calling the QuestionService
        Question question = Question.builder()
                .question(requestDto.getQuestion())
                .optionOne(requestDto.getOptionOne())
                .optionTwo(requestDto.getOptionTwo())
                .optionThree(requestDto.getOptionThree())
                .optionFour(requestDto.getOptionFour())
                .correctAnswer(requestDto.getCorrectAnswer())
                .explanation(requestDto.getExplanation())
                .creator(sage)
                .build();
        Question savedQuestion = questionService.createQuestion(question);

        // 2. Find or create the Tags by calling the TagService
        Set<Tag> tags = tagService.findOrCreateTags(requestDto.getTags(), sage);

        // 3. Create and save the Aphorism
        Aphorism aphorism = Aphorism.builder()
                .title(requestDto.getTitle())
                .sage(sage)
                .question(savedQuestion)
                .tags(tags)
                .build();
        aphorismRepository.save(aphorism);

        // 4. Map to response DTO
        return mapToAphorismResponseDto(aphorism);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AphorismFeedDto> getAllAphorismsForFeed() {
        return aphorismRepository.findAllForFeed().stream()
                .map(this::mapToAphorismFeedDto)
                .collect(Collectors.toList());
    }

    private AphorismFeedDto mapToAphorismFeedDto(Aphorism aphorism) {
        Sage sage = aphorism.getSage();
        Question question = aphorism.getQuestion();

        AphorismFeedDto.SageDetailsDto sageDto = AphorismFeedDto.SageDetailsDto.builder()
                .identity(sage.getIdentity())
                .profileName(sage.getProfileName())
                .profilePicUrl(sage.getProfilePicUrl())
                .country(sage.getCountry())
                .build();

        AphorismFeedDto.QuestionDetailsDto questionDto = AphorismFeedDto.QuestionDetailsDto.builder()
                .question(question.getQuestion())
                .optionOne(question.getOptionOne())
                .optionTwo(question.getOptionTwo())
                .optionThree(question.getOptionThree())
                .optionFour(question.getOptionFour())
                .correctAnswer(question.getCorrectAnswer())
                .explanation(question.getExplanation())
                .aiAnswer(question.getAiAnswer())
                .build();

        return AphorismFeedDto.builder()
                .title(aphorism.getTitle())
                .created(aphorism.getCreated())
                .sage(sageDto)
                .question(questionDto)
                .build();
    }

    private AphorismResponseDto mapToAphorismResponseDto(Aphorism aphorism) {
        return AphorismResponseDto.builder()
                .id(aphorism.getId())
                .title(aphorism.getTitle())
                .created(aphorism.getCreated())
                .sage(AphorismResponseDto.SageDto.builder()
                        .id(aphorism.getSage().getId())
                        .username(aphorism.getSage().getIdentity())
                        .build())
                .question(AphorismResponseDto.QuestionDto.builder()
                        .id(aphorism.getQuestion().getId())
                        .question(aphorism.getQuestion().getQuestion())
                        .build())
                .tags(aphorism.getTags().stream()
                        .map(tag -> AphorismResponseDto.TagDto.builder()
                                .id(tag.getId())
                                .name(tag.getName())
                                .build())
                        .collect(Collectors.toSet()))
                .build();
    }
}
