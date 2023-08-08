package com.jangseop.tokyosubwaydatabase.exception.illegal_format;

import lombok.Getter;

@Getter
public class IllegalFormatException extends RuntimeException {

    protected Object illegalState;

    public IllegalFormatException(String msg, Object illegalState) {
        super(msg);

        this.illegalState = illegalState;
    }

    public IllegalFormatException(String msg) {
        super(msg);
    }
}
