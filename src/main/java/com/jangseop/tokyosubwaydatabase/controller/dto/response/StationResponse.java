package com.jangseop.tokyosubwaydatabase.controller.dto.response;

import com.jangseop.tokyosubwaydatabase.domain.Station;

public record StationResponse(Long id, String nameKr, String nameEn, String nameJp) {

    public static StationResponse of(Station station) {
        return new StationResponse(station.id(), station.nameKr(), station.nameEn(), station.nameJp());
    }
}
