package com.jangseop.tokyosubwaydatabase.exception.not_found;

public class LineStationNotFoundException extends DataNotFoundException {

    public LineStationNotFoundException(Long id) {
        super(String.format("A LineStation of id (%s) is not  found.", id), id);
    }
}
