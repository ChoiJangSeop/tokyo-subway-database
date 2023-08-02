package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.LineStationEntity;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder(access = AccessLevel.PRIVATE)
public record LineStation(Long id, String number, Long lineId, Long stationId, List<TimeUnit> timeTable, double distance,
                          LocalTime departAt) {

    /**
     * of method
     */

    public static LineStation of(LineStationEntity lineStationEntity) {
        return LineStation.builder()
                .id(lineStationEntity.getId())
                .number(lineStationEntity.getNumber())
                .lineId(lineStationEntity.getLine().getId())
                .stationId(lineStationEntity.getStation().getId())
                .distance(lineStationEntity.getDistance())
                .departAt(lineStationEntity.getDepartAt())
                .timeTable(
                        lineStationEntity.getTimeTable().stream()
                                .map(TimeUnit::of)
                                .collect(Collectors.toList())
                ).build();
    }

}
