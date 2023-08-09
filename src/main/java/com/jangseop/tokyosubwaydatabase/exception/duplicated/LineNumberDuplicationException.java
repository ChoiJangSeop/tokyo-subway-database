package com.jangseop.tokyosubwaydatabase.exception.duplicated;

public class LineNumberDuplicationException extends ObjectDuplicatedException {

    public LineNumberDuplicationException(String number) {
        super(String.format("Line number (%s) is already existed", number), number);
    }
}
