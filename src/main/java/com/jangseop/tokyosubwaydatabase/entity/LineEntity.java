package com.jangseop.tokyosubwaydatabase.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "LINE")
public class LineEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private CompanyEntity company;

    @OneToMany(mappedBy = "line")
    private List<LineStationEntity> lineStations = new ArrayList<>();

    @OneToMany(mappedBy = "line")
    private List<FarePolicyEntity> farePolicy = new ArrayList<>();

    private String nameKr;
    private String nameEn;
    private String nameJp;
    private String number;
    private String status;

    /**
     * of method
     */
    public static LineEntity of(String nameKr, String nameEn, String nameJp, String number) {
        LineEntity lineEntity = new LineEntity();
        lineEntity.nameKr = nameKr;
        lineEntity.nameEn = nameEn;
        lineEntity.nameJp = nameJp;
        lineEntity.number = number;

        return lineEntity;
    }

    /**
     * setter method (not used!!)
     */
    public void setCompany(CompanyEntity newCompany) {
        if (this.company != null) {
            this.company.removeLine(this);
        }

        this.company = newCompany;
        newCompany.addLine(this);
    }


}
