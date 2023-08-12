package com.jangseop.tokyosubwaydatabase.exception.notfound;

import com.jangseop.tokyosubwaydatabase.domain.LineStationIdentifier;

public class LineStationNotFoundException extends DataNotFoundException {

    public LineStationNotFoundException(Long id) {
        super(String.format("A LineStation of id (%s) is not  found.", id), id);
    }

    public LineStationNotFoundException(LineStationIdentifier identifier) {
        super(String.format("A LineStation of lineId (%s) and stationId (%s) is not  found.", identifier.lineId(), identifier.stationId()), identifier);
    }
}
