package com.genzsage.genzsage.vote.model;


import com.genzsage.genzsage.aphorism.entity.Aphorism;
import com.genzsage.genzsage.sage.Sage;
import jakarta.persistence.*;

@Entity
@Table(name="votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Sage voter;


    @OneToOne
    private Aphorism aphorism;
}
