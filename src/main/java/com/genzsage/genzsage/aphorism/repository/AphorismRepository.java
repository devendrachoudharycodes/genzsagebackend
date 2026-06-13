package com.genzsage.genzsage.aphorism.repository;

import com.genzsage.genzsage.aphorism.entity.Aphorism;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AphorismRepository extends JpaRepository<Aphorism, Long> {
}
