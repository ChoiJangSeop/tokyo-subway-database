package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.StationEntity;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.*;

import java.util.stream.Collectors;

@Builder(access = AccessLevel.PRIVATE)
public record Station(Long id, List<LineStation> lineStations, String nameKr, String nameEn, String nameJp) {

    public static Station of(StationEntity stationEntity) {
        return Station.builder()
                .id(stationEntity.getId())
                .nameKr(stationEntity.getNameKr())
                .nameJp(stationEntity.getNameJp())
                .nameEn(stationEntity.getNameEn())
                .lineStations(
                        stationEntity.getLineStations().stream()
                                .map(LineStation::of)
                                .collect(Collectors.toList())
                ).build();

    }
}
