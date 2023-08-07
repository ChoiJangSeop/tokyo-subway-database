package com.jangseop.tokyosubwaydatabase.exception;

import lombok.Getter;

@Getter
public class NoUniqueLineStationException extends RuntimeException {

    private final Long lineId;
    private final Long stationId;
    public NoUniqueLineStationException(Long lineId, Long stationId) {
        super(String.format("A line station of line id (%s), station id (%s) is existed", lineId, stationId));
        this.lineId = lineId;
        this.stationId = stationId;
    }
}
