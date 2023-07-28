package com.jangseop.tokyosubwaydatabase.exception;

public class LineNotFoundException extends RuntimeException {
    public LineNotFoundException(Long id) {
        super(String.format("Line id (%s) is not found.", id));
    }

    public LineNotFoundException(String number) {
        super(String.format("Line number (%s) is not found.", number));
    }

}
