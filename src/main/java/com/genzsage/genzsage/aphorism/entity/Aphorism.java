package com.genzsage.genzsage.aphorism.entity;


import com.genzsage.genzsage.question.entity.Question;
import com.genzsage.genzsage.sage.Sage;
import com.genzsage.genzsage.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@ToString
@Entity
@Table(name = "aphorisms")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Aphorism {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @CreationTimestamp(source = SourceType.DB)
    @Column(nullable = false, updatable = false)
    private Instant created;


    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Sage sage;

    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Question question;

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "aphorism_tags",
            joinColumns = @JoinColumn(name = "aphorism_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @ToString.Exclude
    private Set<Tag> tags = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aphorism aphorism = (Aphorism) o;
        return id != 0 && Objects.equals(id, aphorism.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
