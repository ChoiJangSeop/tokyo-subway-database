package com.jangseop.tokyosubwaydatabase.exception;

public class DistanceRangeOverlapException extends RuntimeException {

    public DistanceRangeOverlapException(Double minDistance, Double maxDistance) {
        super(String.format("(%f ~ %f) range overlaps with existed fare policy.",
                minDistance.floatValue(), maxDistance.floatValue()));
    }
}
