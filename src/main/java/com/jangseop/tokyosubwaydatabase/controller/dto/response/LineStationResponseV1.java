package com.jangseop.tokyosubwaydatabase.controller.dto.response;

import com.jangseop.tokyosubwaydatabase.domain.LineStation;
import com.jangseop.tokyosubwaydatabase.domain.Station;

import java.time.LocalTime;

public record LineStationResponseV1(Long id, String number, String nameKr, String nameEn, String nameJp, Double distance, LocalTime departAt) {

    public static LineStationResponseV1 of(LineStation lineStation, Station station) {
        return new LineStationResponseV1(
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
