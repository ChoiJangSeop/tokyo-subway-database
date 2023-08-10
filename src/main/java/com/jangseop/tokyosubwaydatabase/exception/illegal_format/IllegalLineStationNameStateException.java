package com.jangseop.tokyosubwaydatabase.exception.illegal_format;

import com.jangseop.tokyosubwaydatabase.exception.illegal_format.IllegalFormatException;

public class IllegalLineStationNameStateException extends IllegalFormatException {

    public IllegalLineStationNameStateException(String lineNumber) {
        super(String.format("A Line of number (%s) is not existed", lineNumber), lineNumber);
    }

    public IllegalLineStationNameStateException() {
        super("line station number is illegal format");
    }
}
