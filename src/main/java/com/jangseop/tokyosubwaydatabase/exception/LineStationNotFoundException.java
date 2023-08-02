package com.jangseop.tokyosubwaydatabase.exception;

public class LineStationNotFoundException extends RuntimeException {

    public LineStationNotFoundException(Long id) {
        super(String.format("A LineStation of id (%s) is not  found.", id));
    }
}
