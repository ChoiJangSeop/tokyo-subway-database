package com.jangseop.tokyosubwaydatabase.exception;

public class IllegalLineStationNameStateException extends RuntimeException {

    public IllegalLineStationNameStateException(String lineNumber) {
        super(String.format("A Line of number (%s) is not existed", lineNumber));
    }
}
