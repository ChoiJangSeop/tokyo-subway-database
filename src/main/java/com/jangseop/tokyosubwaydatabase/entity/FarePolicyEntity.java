package com.jangseop.tokyosubwaydatabase.entity;

import com.jangseop.tokyosubwaydatabase.domain.Line;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "FARE_POLICY")
public class FarePolicyEntity {

    @Id @GeneratedValue
    @Column(name = "FARE_POLICY_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "LINE_ID")
    private LineEntity line;

    @Column(name = "LEFT_DISTANCE")
    private Double minDistance;

    @Column(name = "RIGHT_DISTANCE")
    private Double maxDistance;

    private int fare;
}
