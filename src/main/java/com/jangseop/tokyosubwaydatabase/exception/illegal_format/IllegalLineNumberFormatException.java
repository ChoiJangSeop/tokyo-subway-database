package com.jangseop.tokyosubwaydatabase.exception.illegal_format;

public class IllegalLineNumberFormatException extends IllegalFormatException {

    public IllegalLineNumberFormatException(String number) {
        super("Line's number must consist of english only.", number);
    }
}
