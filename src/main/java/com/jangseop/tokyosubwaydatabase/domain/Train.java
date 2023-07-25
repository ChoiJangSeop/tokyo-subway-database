package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.TrainEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class Train {

    private final Long id;

    private final List<TimeUnit> timeTable;

    private final String name;

    /**
     * create method
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
