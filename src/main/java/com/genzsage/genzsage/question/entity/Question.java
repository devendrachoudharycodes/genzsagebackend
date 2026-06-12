package com.genzsage.genzsage.question.entity;


import com.genzsage.genzsage.sage.Sage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "questions")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sage_id", nullable = false)
    @ToString.Exclude
    private Sage creator;

    @NotBlank
    @Size(max = 500)
    @Column(length = 500)
    private String question;

    @NotBlank
    private String optionOne;

    @NotBlank
    private String optionTwo;

    @NotBlank
    private String optionThree;

    @NotBlank
    private String optionFour;

    @CreationTimestamp(source = SourceType.DB)
    @Column(nullable = false, updatable = false)
    private Instant creationDateTime;

    private Integer correctAnswer;

    @Column(length = 2000)
    private String explanation;
    
    private String aiAnswer="";

    @Builder.Default
    private boolean deleted = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id != null && Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
