package com.jangseop.tokyosubwaydatabase.entity;


import com.jangseop.tokyosubwaydatabase.domain.Company;
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

    /**
     * of method
     */
    public static CompanyEntity of(String name) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.name = name;
        return companyEntity;
    }

    /**
     * business method
     */
    public void addLine(LineEntity newLine) {
        if (this.lines.contains(newLine)) return;
        this.lines.add(newLine);
    }

    public void removeLine(LineEntity line) {
        this.lines.remove(line);
    }
}
