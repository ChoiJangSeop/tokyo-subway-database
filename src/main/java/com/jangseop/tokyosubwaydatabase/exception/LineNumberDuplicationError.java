package com.jangseop.tokyosubwaydatabase.exception;

public class LineNumberDuplicationError extends RuntimeException {

    public LineNumberDuplicationError(String number) {
        super(String.format("Line number (%s) is already existed", number));
    }
}
