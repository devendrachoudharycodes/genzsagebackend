package com.genzsage.genzsage.aphorism.repository;

import com.genzsage.genzsage.aphorism.entity.Aphorism;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AphorismRepository extends JpaRepository<Aphorism, Long> {

    @Query("SELECT a FROM Aphorism a JOIN FETCH a.sage JOIN FETCH a.question")
    List<Aphorism> findAllForFeed();
}
