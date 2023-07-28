package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;


@Builder(access = AccessLevel.PRIVATE)
public record Line(Long id, Long companyId, String nameKr, String nameJp, String nameEn, String number,
                   String status, List<LineStation> lineStations, List<FarePolicy> farePolicies) {

    /**
     * of method
     */

    // TODO 양방향 참조(Line, Company)를 할경우, of 메서드의 무한참조 문제 -> 일단은 외래키 참조로 해결
    public static Line of(LineEntity lineEntity) {
        return Line.builder()
                .id(lineEntity.getId())
                .companyId(lineEntity.getCompany().getId())
                .nameKr(lineEntity.getNameKr())
                .nameEn(lineEntity.getNameEn())
                .nameJp(lineEntity.getNameJp())
                .status(lineEntity.getStatus())
                .number(lineEntity.getNumber())
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
