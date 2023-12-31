package com.jangseop.tokyosubwaydatabase.controller.dto.request;

public record FarePolicyCreateRequest(Long lineId, Double minDistance, Double maxDistance, int fare) {

    public static FarePolicyCreateRequest of(Long lineId, Double minDistance, Double maxDistance, int fare) {
        return new FarePolicyCreateRequest(lineId, minDistance, maxDistance, fare);
    }
}
