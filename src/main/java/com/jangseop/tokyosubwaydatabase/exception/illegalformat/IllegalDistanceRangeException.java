package com.jangseop.tokyosubwaydatabase.exception.illegalformat;

import static com.jangseop.tokyosubwaydatabase.exception.illegalformat.DistanceRangeOverlapException.*;

public class IllegalDistanceRangeException extends IllegalFormatException {

    public IllegalDistanceRangeException(FareRange range) {
        super("right bound is less then left bound", range);
    }
}
