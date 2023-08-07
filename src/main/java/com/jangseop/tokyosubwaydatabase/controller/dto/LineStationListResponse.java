package com.jangseop.tokyosubwaydatabase.controller.dto;

import java.util.List;

public record LineStationListResponse(List<LineStationResponse> stations) {

    public static LineStationListResponse of(List<LineStationResponse> lineStationsResponses) {
        return new LineStationListResponse(lineStationsResponses);
    }
}
