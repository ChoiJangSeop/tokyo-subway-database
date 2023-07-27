package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;


@Builder(access = AccessLevel.PRIVATE)
public record Line(Long id, Company company, String nameKr, String nameJp, String nameEn, String shortName,
                   String status, List<LineStation> lineStations, List<FarePolicy> farePolicies) {

    /**
     * of method
     */

    public static Line of(LineEntity lineEntity) {
        return Line.builder()
                .id(lineEntity.getId())
                .company(Company.of(lineEntity.getCompany()))
                .nameKr(lineEntity.getNameKr())
                .nameEn(lineEntity.getNameEn())
                .nameJp(lineEntity.getNameJp())
                .status(lineEntity.getStatus())
                .lineStations(
                        lineEntity.getLineStations().stream()
                                .map(LineStation::of)
                                .collect(Collectors.toList())
                )
                .farePolicies(
                        lineEntity.getFarePolicy().stream()
                                .map(FarePolicy::of)
                                .collect(Collectors.toList())
                ).build();
    }
}
