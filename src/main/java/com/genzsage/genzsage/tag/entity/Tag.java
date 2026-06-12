package com.genzsage.genzsage.tag.entity;


import com.genzsage.genzsage.aphorism.entity.Aphorism;
import com.genzsage.genzsage.sage.Sage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(
        name = "tags"
)
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank(message = "Tag name cannot be blank")
    @Size(min = 2, max = 100)
    @Column(nullable = false, unique = true)
    private String name;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    @ToString.Exclude
    private Sage creator;

    @CreationTimestamp(source = SourceType.DB)
    @Column(nullable = false, updatable = false)
    private Instant createdAt;


    /**
     * Aphorisms using this tag
     */
    @Builder.Default
    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    private Set<Aphorism> aphorisms = new HashSet<>();



    /**
     * Users interested in this tag
     */
    @Builder.Default
    @ManyToMany(mappedBy = "interests")
    @ToString.Exclude
    private Set<Sage> interestedSages = new HashSet<>();
    
    @Builder.Default
    private Long usageCount=0L;

    @Builder.Default
    @Column(nullable = false)
    private boolean deleted = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return id != null && Objects.equals(id, tag.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
