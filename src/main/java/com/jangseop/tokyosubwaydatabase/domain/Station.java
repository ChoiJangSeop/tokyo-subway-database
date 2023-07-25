package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.StationEntity;
import lombok.Builder;
import lombok.Getter;
import java.util.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
@Builder
public class Station {

    private final Long id;

    private final List<LineStation> lineStations;

    private final String nameKr;

    private final String nameEn;

    private final String nameJp;

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
