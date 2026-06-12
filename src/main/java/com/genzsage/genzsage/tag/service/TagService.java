package com.genzsage.genzsage.tag.service;

import com.genzsage.genzsage.tag.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {
    Tag createTag(Tag tag);
    Optional<Tag> getTagById(Long id);
    List<Tag> getAllTags();
    Tag updateTag(Tag tag);
    void deleteTag(Long id);
}
