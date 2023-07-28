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
}
