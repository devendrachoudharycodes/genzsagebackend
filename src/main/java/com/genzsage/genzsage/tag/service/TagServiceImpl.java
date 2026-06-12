package com.genzsage.genzsage.tag.service;

import com.genzsage.genzsage.tag.entity.Tag;
import com.genzsage.genzsage.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Tag createTag(Tag tag) {
        Optional<Tag> existingTag = tagRepository.findByName(tag.getName());

        if (existingTag.isPresent()) {
            if (existingTag.get().isDeleted()) {
                throw new IllegalStateException("This tag has been banned and cannot be created again.");
            }
            return existingTag.get();
        }

        return tagRepository.save(tag);
    }

    @Override
    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findByIdAndDeletedFalse(id);
    }

    @Override
    public List<Tag> getAllTags() {
        return tagRepository.findAllByDeletedFalse();
    }

    @Override
    public Tag updateTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void deleteTag(Long id) {
        tagRepository.findById(id).ifPresent(tag -> {
            tag.setDeleted(true);
            tagRepository.save(tag);
        });
    }
}
