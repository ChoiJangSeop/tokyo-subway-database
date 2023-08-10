package com.jangseop.tokyosubwaydatabase.controller.dto.response.line_station;

import java.util.List;

public record LineStationListResponse(List<LineStationResponse> lineStations) {

    public static LineStationListResponse of(List<LineStationResponse> lineStations) {
        return new LineStationListResponse(lineStations);
    }
}
