package com.jangseop.tokyosubwaydatabase.exception.illegalformat;

public class IllegalLineStationNameStateException extends IllegalFormatException {

    public IllegalLineStationNameStateException(String lineNumber) {
        super(String.format("A Line of number (%s) is not existed", lineNumber), lineNumber);
    }

    public IllegalLineStationNameStateException() {
        super("line station number is illegal format");
    }
}
