package com.jangseop.tokyosubwaydatabase.exception.not_found;

public class StationNotFoundException extends DataNotFoundException {

    public StationNotFoundException(Long id) {
        super(String.format("A Station of id (%s) is not found.", id), id);
    }
}
