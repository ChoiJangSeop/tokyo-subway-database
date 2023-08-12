package com.jangseop.tokyosubwaydatabase.exception.illegalformat;

public class IllegalFareException extends IllegalFormatException {

    public IllegalFareException(int fare) {
        super("A fare must be better or same then 0", fare);
    }
}
