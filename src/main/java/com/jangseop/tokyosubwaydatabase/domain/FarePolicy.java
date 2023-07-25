package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.FarePolicyEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FarePolicy {

    private final Long id;

    private final Line line;

    private Double minDistance;

    private Double maxDistance;

    private int fare;

    /**
     * of method
     */

    public static FarePolicy of(FarePolicyEntity farePolicyEntity) {
        return FarePolicy.builder()
                .id(farePolicyEntity.getId())
                .line(Line.of(farePolicyEntity.getLine()))
                .minDistance(farePolicyEntity.getMinDistance())
                .maxDistance(farePolicyEntity.getMaxDistance())
                .build();
    }
}
