package com.genzsage.genzsage.sage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SageRepository extends JpaRepository<Sage, Long> {

    Optional<Sage> findByIdentity(String identity);

    Optional<Sage> findByPhoneNumber(String phoneNumber);

    Optional<Sage> findByEmail(String email);

    @Query("SELECT s FROM Sage s WHERE s.identity = :input OR s.email = :input OR s.phoneNumber = :input")
    Sage findByIdentityOrEmailOrPhoneNumber(@Param("input") String username);

    // Refactored to match Tag entity field name 'name'
    @Query("SELECT s FROM Sage s JOIN s.interests t WHERE t.name = :tagName")
    List<Sage> findAllByInterestName(@Param("tagName") String tagName);

}