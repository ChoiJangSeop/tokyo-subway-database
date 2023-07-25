package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.TimeUnitEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@Builder
public class TimeUnit {

    private final Long id;

    private final Train train;

    private final LineStation lineStation;

    private final LocalTime departAt;

    /**
     * of method
     */

    public static TimeUnit of(TimeUnitEntity timeUnitEntity) {
        return TimeUnit.builder()
                .id(timeUnitEntity.getId())
                .train(Train.of(timeUnitEntity.getTrain()))
                .lineStation(LineStation.of(timeUnitEntity.getLineStation()))
                .departAt(timeUnitEntity.getDepartAt())
                .build();
    }
}
