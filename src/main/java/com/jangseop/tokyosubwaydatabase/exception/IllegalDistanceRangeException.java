package com.jangseop.tokyosubwaydatabase.exception;

public class IllegalDistanceRangeException extends RuntimeException {

    public IllegalDistanceRangeException() {
        super("right bound is less then left bound");
    }
}
