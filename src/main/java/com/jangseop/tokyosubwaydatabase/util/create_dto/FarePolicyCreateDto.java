package com.jangseop.tokyosubwaydatabase.util.create_dto;

import com.jangseop.tokyosubwaydatabase.service.FarePolicyService;

public record FarePolicyCreateDto(Long lineId, Double minDistance, Double maxDistance, int fare) {

    public static FarePolicyCreateDto of(Long lineId, Double minDistance, Double maxDistance, int fare) {
        return new FarePolicyCreateDto(lineId, minDistance, maxDistance, fare);
    }
}
