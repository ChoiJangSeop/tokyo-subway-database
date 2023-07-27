package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.TimeUnitEntity;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalTime;

@Builder(access = AccessLevel.PRIVATE)
public record TimeUnit(Long id, Train train, LineStation lineStation, LocalTime departAt) {

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
