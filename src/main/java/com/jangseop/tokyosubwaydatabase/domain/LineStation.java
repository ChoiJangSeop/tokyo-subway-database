package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.LineStationEntity;
import lombok.Builder;
import lombok.Getter;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class LineStation {

    private final Long id;

    private final Station station;

    private final Line line;

    private final String name;

    private final List<TimeUnit> timeTable;

    private final double distance;

    private final LocalTime departAt;


    /**
     * of method
     */

    public static LineStation of(LineStationEntity lineStationEntity) {
        return LineStation.builder()
                .id(lineStationEntity.getId())
                .station(Station.of(lineStationEntity.getStation()))
                .line(Line.of(lineStationEntity.getLine()))
                .name(lineStationEntity.getName())
                .distance(lineStationEntity.getDistance())
                .departAt(lineStationEntity.getDepartAt())
                .timeTable(
                        lineStationEntity.getTimeTable().stream()
                                .map(TimeUnit::of)
                                .collect(Collectors.toList())
                ).build();
    }

}
