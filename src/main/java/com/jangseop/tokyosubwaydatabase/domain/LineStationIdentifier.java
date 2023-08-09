package com.jangseop.tokyosubwaydatabase.domain;

public record LineStationIdentifier(Long lineId, Long stationId) {

    public static LineStationIdentifier of(Long lineId, Long stationId) {
        return new LineStationIdentifier(lineId, stationId);
    }
}
