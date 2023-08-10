package com.jangseop.tokyosubwaydatabase.controller.dto.response;

import java.util.List;

public record LineStationListResponseV1(List<LineStationResponseV1> stations) {

    public static LineStationListResponseV1 of(List<LineStationResponseV1> lineStationsResponses) {
        return new LineStationListResponseV1(lineStationsResponses);
    }
}
