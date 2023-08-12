package com.jangseop.tokyosubwaydatabase.exception.illegalformat;

public class DistanceRangeOverlapException extends IllegalFormatException {

    public DistanceRangeOverlapException(FareRange range) {
        super(String.format("(%f ~ %f) range overlaps with existed fare policy.",
                range.minDistance().floatValue(), range.maxDistance().floatValue()), range);
    }

    public record FareRange(Double minDistance, Double maxDistance) {}
}
