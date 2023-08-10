package com.jangseop.tokyosubwaydatabase.controller.dto.response;

import com.jangseop.tokyosubwaydatabase.domain.Company;
import com.jangseop.tokyosubwaydatabase.domain.Line;
import com.jangseop.tokyosubwaydatabase.domain.LineStation;

public record StationLineResponse(Long id, String number, String company, String nameKr, String nameEn, String nameJp, Double distance) {

    public static StationLineResponse of(LineStation lineStation, Line line, Company company) {
        return new StationLineResponse(
                lineStation.id(), lineStation.number(),
                company.name(),
                line.nameKr(), line.nameEn(), line.nameJp(),
                lineStation.distance());
    }
}
