package com.genzsage.genzsage.vote.model;

import com.genzsage.genzsage.aphorism.entity.Aphorism;
import com.genzsage.genzsage.sage.Sage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "votes", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"sage_id", "aphorism_id"})
})
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sage_id", nullable = false)
    private Sage sage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aphorism_id", nullable = false)
    private Aphorism aphorism;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
