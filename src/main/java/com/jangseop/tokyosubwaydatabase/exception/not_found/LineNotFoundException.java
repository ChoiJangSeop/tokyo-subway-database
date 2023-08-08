package com.jangseop.tokyosubwaydatabase.exception.not_found;

public class LineNotFoundException extends DataNotFoundException {
    public LineNotFoundException(Long id) {
        super(String.format("Line id (%s) is not found.", id), id);
    }

    public LineNotFoundException(String number) {
        super(String.format("Line number (%s) is not found.", number), number);
    }

}
