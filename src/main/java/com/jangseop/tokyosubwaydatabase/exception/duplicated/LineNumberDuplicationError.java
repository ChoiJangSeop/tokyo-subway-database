package com.jangseop.tokyosubwaydatabase.exception.duplicated;

public class LineNumberDuplicationError extends ObjectDuplicatedException {

    public LineNumberDuplicationError(String number) {
        super(String.format("Line number (%s) is already existed", number), number);
    }
}
