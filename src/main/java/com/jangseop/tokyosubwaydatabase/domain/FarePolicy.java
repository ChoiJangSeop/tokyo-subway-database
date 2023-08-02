package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.FarePolicyEntity;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.Objects;


@Builder(access = AccessLevel.PRIVATE)
public record FarePolicy(Long id, Long lineId, Double minDistance, Double maxDistance, int fare) {


    /**
     * of method
     */

    public static FarePolicy of(FarePolicyEntity farePolicyEntity) {

        return new FarePolicy(
                farePolicyEntity.getId(),
                farePolicyEntity.getLine().getId(),
                farePolicyEntity.getMinDistance(),
                farePolicyEntity.getMaxDistance(),
                farePolicyEntity.getFare()
        );
    }
}
