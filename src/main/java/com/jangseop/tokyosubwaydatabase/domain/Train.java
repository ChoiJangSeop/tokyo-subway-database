package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.TrainEntity;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder(access = AccessLevel.PRIVATE)
public record Train(Long id, List<TimeUnit> timeTable, String name) {

    /**
     * of method
     */

    public static Train of(TrainEntity trainEntity) {
        return Train.builder()
                .id(trainEntity.getId())
                .name(trainEntity.getName())
                .timeTable(
                        trainEntity.getTimeTable().stream()
                                .map(TimeUnit::of)
                                .collect(Collectors.toList())
                ).build();
    }
}
