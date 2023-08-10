package com.jangseop.tokyosubwaydatabase.controller.dto.response;

import com.jangseop.tokyosubwaydatabase.domain.FarePolicy;

public record FarePolicyResponse(Long id, Double minDistance, Double maxDistance, int fare) {

    public static FarePolicyResponse of(FarePolicy farePolicy) {
        return new FarePolicyResponse(
                farePolicy.id(),
                farePolicy.minDistance(),
                farePolicy.maxDistance(),
                farePolicy.fare());
    }
}
