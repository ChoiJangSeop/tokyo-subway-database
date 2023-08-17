package com.jangseop.tokyosubwaydatabase.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "LINE_STATION")
public class LineStationEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINE_STATION_ID")
    private Long id;

    private String number;

    @ManyToOne
    @JoinColumn(name = "LINE_ID")
    private LineEntity line;

    @ManyToOne
    @JoinColumn(name = "STATION_ID")
    private StationEntity station;

    @OneToMany(mappedBy = "lineStation")
    private List<TimeUnitEntity> timeTable = new ArrayList<>();

    private double distance;

    // implement later
    private LocalTime departAt = LocalTime.NOON;

    /**
     * of method
     */
    public static LineStationEntity of(String number, double distance) {
        LineStationEntity lineStationEntity = new LineStationEntity();
        lineStationEntity.number = number;
        lineStationEntity.distance = distance;

        return lineStationEntity;
    }

    /**
     * 연관관계 편의 메서드
     */
    public void setLine(LineEntity lineEntity) {
        if (this.line != null) {
            this.line.getLineStations().remove(this);
        }

        this.line = lineEntity;
        lineEntity.getLineStations().add(this);
    }

    public void setStation(StationEntity stationEntity) {
        if (this.station != null) {
            this.station.getLineStations().remove(this);
        }

        this.station = stationEntity;
        stationEntity.getLineStations().add(this);
    }

}
