package com.jangseop.tokyosubwaydatabase.util.createdto;

// 사용하는 쪽에
public record LineStationCreateDto(String name, Long lineId, Long stationId, double distance) {
    public static LineStationCreateDto of(String name, Long lineId, Long stationId, double distance) {
        return new LineStationCreateDto(name, lineId, stationId, distance);
    }
}
