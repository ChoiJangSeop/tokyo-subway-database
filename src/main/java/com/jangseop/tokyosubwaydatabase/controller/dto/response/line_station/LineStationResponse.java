package com.jangseop.tokyosubwaydatabase.controller.dto.response.line_station;

import com.jangseop.tokyosubwaydatabase.domain.Line;
import com.jangseop.tokyosubwaydatabase.domain.LineStation;
import com.jangseop.tokyosubwaydatabase.domain.Station;

import java.time.LocalTime;

public record LineStationResponse(Long id, Long lineId, NameSet lineName, Long stationId, NameSet stationName, String number, double distance, LocalTime departAt) {

    public static LineStationResponse of(LineStation lineStation, Line line, Station station) {
        return new LineStationResponse(
                lineStation.id(),
                line.id(),
                new NameSet(line.nameKr(), line.nameEn(), line.nameJp()),
                station.id(),
                new NameSet(station.nameKr(), station.nameEn(), station.nameJp()),
                lineStation.number(),
                lineStation.distance(),
                lineStation.departAt());
    }

}
