package com.jangseop.tokyosubwaydatabase.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class LineStationEntity {

    @Id @GeneratedValue
    @Column(name = "LINE_STATION_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "LINE_ID")
    private LineEntity line;

    @ManyToOne
    @JoinColumn(name = "STATION_ID")
    private StationEntity station;

    @OneToMany(mappedBy = "lineStation")
    private List<TimeUnitEntity> timeTable = new ArrayList<>();

    private String name;

    private double distance;

    private LocalTime departAt;

}