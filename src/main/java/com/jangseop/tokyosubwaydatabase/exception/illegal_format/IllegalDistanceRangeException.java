package com.jangseop.tokyosubwaydatabase.exception.illegal_format;

import com.jangseop.tokyosubwaydatabase.exception.illegal_format.IllegalFormatException;

import static com.jangseop.tokyosubwaydatabase.exception.illegal_format.DistanceRangeOverlapException.*;

public class IllegalDistanceRangeException extends IllegalFormatException {

    public IllegalDistanceRangeException(FareRange range) {
        super("right bound is less then left bound", range);
    }
}
