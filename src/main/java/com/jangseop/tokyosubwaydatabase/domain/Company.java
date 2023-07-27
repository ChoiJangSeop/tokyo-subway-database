package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.CompanyEntity;

import java.util.List;
import java.util.stream.Collectors;

public record Company(Long id, String name, List<Line> lines) {

    /**
     * of method
     */

    public static Company of(CompanyEntity companyEntity) {
        return new Company(
                companyEntity.getId(),
                companyEntity.getName(),
                companyEntity.getLines().stream()
                        .map(Line::of)
                        .collect(Collectors.toList())
        );
    }
}
