package com.jangseop.tokyosubwaydatabase.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "STATION")
public class StationEntity {

    @Id @GeneratedValue
    @Column(name = "STATION_ID")
    private Long id;

    @OneToMany(mappedBy = "station")
    private List<LineStationEntity> lineStations = new ArrayList<>();

    private String nameKr;
    private String nameEn;
    private String nameJp;

    /**
     * of method
     */
    public static StationEntity of(String nameKr, String nameEn, String nameJp) {

        StationEntity stationEntity = new StationEntity();

        stationEntity.nameKr = nameKr;
        stationEntity.nameEn = nameEn;
        stationEntity.nameJp = nameJp;

        return stationEntity;
    }
}
