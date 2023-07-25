package com.jangseop.tokyosubwaydatabase.domain;

import com.jangseop.tokyosubwaydatabase.entity.CompanyEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class Company {

    private final Long id;

    private final String name;

    private final List<Line> lines;

    /**
     * of method
     */

    public static Company of(CompanyEntity companyEntity) {
        return Company.builder()
                .id(companyEntity.getId())
                .name(companyEntity.getName())
                .lines(
                        companyEntity.getLines().stream()
                                .map(Line::of)
                                .collect(Collectors.toList())
                ).build();
    }
}
