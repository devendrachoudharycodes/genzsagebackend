package com.genzsage.genzsage.vote.service;

import com.genzsage.genzsage.aphorism.entity.Aphorism;
import com.genzsage.genzsage.aphorism.repository.AphorismRepository;
import com.genzsage.genzsage.auth.AuthService;
import com.genzsage.genzsage.sage.Sage;
import com.genzsage.genzsage.vote.model.Vote;
import com.genzsage.genzsage.vote.repository.VoteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final AphorismRepository aphorismRepository;
    private final AuthService authService;

    @Override
    @Transactional
    public void castVote(Long aphorismId, Double value) {
        Sage currentUser = authService.getCurrentUser();
        Aphorism aphorism = aphorismRepository.findById(aphorismId)
                .orElseThrow(() -> new EntityNotFoundException("Aphorism not found with id: " + aphorismId));

        Optional<Vote> existingVote = voteRepository.findBySageAndAphorism(currentUser, aphorism);

        if (existingVote.isPresent()) {
            // Update existing vote
            existingVote.get().setValue(value);
            voteRepository.save(existingVote.get());
        } else {
            // Create new vote
            Vote newVote = Vote.builder()
                    .sage(currentUser)
                    .aphorism(aphorism)
                    .value(value)
                    .build();
            voteRepository.save(newVote);
        }

        // Recalculate and update the average rating
        List<Double> allVotes = voteRepository.findAllValuesByAphorism(aphorism);
        double average = allVotes.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        aphorism.setAverageRating(average);
        aphorism.setVoteCount(allVotes.size());
        aphorismRepository.save(aphorism);
    }
}
