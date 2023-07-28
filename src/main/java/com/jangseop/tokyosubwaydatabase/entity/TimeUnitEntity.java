package com.jangseop.tokyosubwaydatabase.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalTime;

@Entity
@Getter
@Table(name = "TIME_UNIT")
public class TimeUnitEntity {

    @Id @GeneratedValue
    @Column(name = "TIME_UNIT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TRAIN_ID")
    private TrainEntity train;

    @ManyToOne
    @JoinColumn(name = "LINE_STATION_ID")
    private LineStationEntity lineStation;

    private LocalTime departAt;
}
