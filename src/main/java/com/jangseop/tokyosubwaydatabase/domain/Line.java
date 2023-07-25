package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class Line {

    private final Long id;

    private final Company company;

    private final String nameKr;

    private final String nameJp;

    private final String nameEn;

    private final String shortName;

    private final String status;

    private final List<LineStation> lineStations;

    private final List<FarePolicy> farePolicies;

    /**
     * create method
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
