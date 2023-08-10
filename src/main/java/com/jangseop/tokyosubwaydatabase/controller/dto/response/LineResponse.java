package com.jangseop.tokyosubwaydatabase.controller.dto.response;

import com.jangseop.tokyosubwaydatabase.domain.Line;

public record LineResponse(Long id, String number, String companyName, String nameKr, String nameEn, String nameJp) {

    public static LineResponse of(Line line, String companyName) {
        return new LineResponse(
                line.id(),
                line.number(),
                companyName,
                line.nameKr(),
                line.nameEn(),
                line.nameJp()
        );
    }
}
