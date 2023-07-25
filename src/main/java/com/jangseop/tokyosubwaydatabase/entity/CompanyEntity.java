package com.jangseop.tokyosubwaydatabase.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class CompanyEntity {

    @Id @GeneratedValue
    @Column(name = "COMPANY_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "company")
    private List<LineEntity> lines = new ArrayList<>();
}
