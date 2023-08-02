package com.jangseop.tokyosubwaydatabase.exception;

public class StationNotFoundException extends RuntimeException {

    public StationNotFoundException(Long id) {
        super(String.format("A Station of id (%s) is not found.", id));
    }
}
