package com.jangseop.tokyosubwaydatabase.exception.not_found;

import lombok.Getter;

@Getter
public class DataNotFoundException extends RuntimeException {

    protected Object identifier;

    public DataNotFoundException(String msg, Object identifier) {
        super(msg);
        this.identifier = identifier;
    }
}
