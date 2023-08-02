package com.jangseop.tokyosubwaydatabase.entity;

import com.jangseop.tokyosubwaydatabase.domain.FarePolicy;
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

    /**
     * of method
     */
    public static FarePolicyEntity of(Double minDistance, Double maxDistance, int fare) {

        FarePolicyEntity farePolicyEntity = new FarePolicyEntity();

        farePolicyEntity.minDistance = minDistance;
        farePolicyEntity.maxDistance = maxDistance;
        farePolicyEntity.fare = fare;

        return farePolicyEntity;
    }

    /**
     * 연관관계 편의 메서드
     */
    public void setLine(LineEntity line) {

        if (this.getLine() != null) {
            this.getLine().getFarePolicy().remove(this);
        }

        this.line = line;
        line.getFarePolicy().add(this);
    }
}
