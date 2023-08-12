package com.jangseop.tokyosubwaydatabase.exception.illegalformat;

public class IllegalLineNumberFormatException extends IllegalFormatException {

    public IllegalLineNumberFormatException(String number) {
        super("Line's number must consist of english only.", number);
    }
}
