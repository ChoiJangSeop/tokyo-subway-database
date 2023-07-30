package com.jangseop.tokyosubwaydatabase.exception;

public class IllegalFareException extends RuntimeException {

    public IllegalFareException() {
        super("A fare must be better or same then 0");
    }
}
