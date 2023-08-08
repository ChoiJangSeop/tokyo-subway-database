package com.jangseop.tokyosubwaydatabase.exception.duplicated;

import lombok.Getter;

@Getter
public class LineStationDuplicatedException extends ObjectDuplicatedException {

    public LineStationDuplicatedException(LineStationIdentifier identifier) {
        super(String.format("A line station of line id (%s), station id (%s) is existed", identifier.lineId, identifier.stationId()), identifier);
    }

    public record LineStationIdentifier(Long lineId, Long stationId) {}
}
