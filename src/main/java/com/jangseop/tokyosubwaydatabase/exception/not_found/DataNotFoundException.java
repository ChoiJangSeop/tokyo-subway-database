package com.jangseop.tokyosubwaydatabase.exception.not_found;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {

    protected Long id;

    protected String identifier;

    public DataNotFoundException(String msg, Long id) {
        super(msg);
        this.id = id;
    }

    public DataNotFoundException(String msg, String identifier) {
        super(msg);
        this.identifier = identifier;
    }
}
