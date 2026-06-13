package com.genzsage.genzsage.tag.service;

import com.genzsage.genzsage.sage.Sage;
import com.genzsage.genzsage.tag.entity.Tag;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TagService {
    Tag createTag(Tag tag);
    Optional<Tag> getTagById(Long id);
    List<Tag> getAllTags();
    Tag updateTag(Tag tag);
    void deleteTag(Long id);
    Set<Tag> findOrCreateTags(Set<String> tagNames, Sage creator);
}
