package com.genzsage.genzsage.vote.repository;

import com.genzsage.genzsage.aphorism.entity.Aphorism;
import com.genzsage.genzsage.sage.Sage;
import com.genzsage.genzsage.vote.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findBySageAndAphorism(Sage sage, Aphorism aphorism);

    @Query("SELECT v.value FROM Vote v WHERE v.aphorism = :aphorism")
    List<Double> findAllValuesByAphorism(Aphorism aphorism);
}
