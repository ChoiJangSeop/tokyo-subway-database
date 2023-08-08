package com.jangseop.tokyosubwaydatabase.exception.duplicated;

public class ObjectDuplicatedException extends RuntimeException {

    protected Object duplicated;

    public ObjectDuplicatedException(String msg, Object duplicated) {
        super(msg);
        this.duplicated = duplicated;
    }
}
