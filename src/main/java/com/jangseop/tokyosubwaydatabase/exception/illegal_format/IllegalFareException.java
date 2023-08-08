package com.jangseop.tokyosubwaydatabase.exception.illegal_format;

import com.jangseop.tokyosubwaydatabase.exception.illegal_format.IllegalFormatException;

public class IllegalFareException extends IllegalFormatException {

    public IllegalFareException(int fare) {
        super("A fare must be better or same then 0", fare);
    }
}
