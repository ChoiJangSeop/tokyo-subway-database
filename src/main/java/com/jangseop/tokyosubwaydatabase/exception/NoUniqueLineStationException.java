package com.jangseop.tokyosubwaydatabase.exception;

public class NoUniqueLineStationException extends RuntimeException {

    public NoUniqueLineStationException(Long lineId, Long stationId) {
        super(String.format("A line station of line id (%s), station id (%s) is existed", lineId, stationId));
    }
}
