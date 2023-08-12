package com.jangseop.tokyosubwaydatabase.util.createdto;

public record FarePolicyCreateDto(Long lineId, Double minDistance, Double maxDistance, int fare) {

    public static FarePolicyCreateDto of(Long lineId, Double minDistance, Double maxDistance, int fare) {
        return new FarePolicyCreateDto(lineId, minDistance, maxDistance, fare);
    }
}
