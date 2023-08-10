package com.jangseop.tokyosubwaydatabase.controller.dto;

public record LineStationCreateRequest(String number, Long lineId, Long stationId, double distance) {

    public static LineStationCreateRequest of(String number, Long lineId, Long stationId, double distance) {
        return new LineStationCreateRequest(number, lineId, stationId, distance);
    }
}
