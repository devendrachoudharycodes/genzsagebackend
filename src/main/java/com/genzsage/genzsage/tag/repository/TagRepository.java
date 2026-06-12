package com.genzsage.genzsage.tag.repository;

import com.genzsage.genzsage.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
    Optional<Tag> findByIdAndDeletedFalse(Long id);
    List<Tag> findAllByDeletedFalse();
}
