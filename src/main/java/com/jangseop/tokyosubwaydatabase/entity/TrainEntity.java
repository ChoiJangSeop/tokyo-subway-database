package com.jangseop.tokyosubwaydatabase.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class TrainEntity {

    @Id @GeneratedValue
    @Column(name = "TRAIN_ID")
    private Long id;

    @OneToMany(mappedBy = "train")
    private List<TimeUnitEntity> timeTable = new ArrayList<>();

    private String name;
}
