package com.jangseop.tokyosubwaydatabase.controller.dto.response;

import com.jangseop.tokyosubwaydatabase.domain.LineStation;
import com.jangseop.tokyosubwaydatabase.domain.Station;

import java.time.LocalTime;

public record LineStationResponse(Long id, String number, String nameKr, String nameEn, String nameJp, Double distance, LocalTime departAt) {

    public static LineStationResponse of(LineStation lineStation, Station station) {
        return new LineStationResponse(
                lineStation.id(),
                lineStation.number(),
                station.nameKr(),
                station.nameEn(),
                station.nameJp(),
                lineStation.distance(),
                lineStation.departAt()
        );
    }
}
