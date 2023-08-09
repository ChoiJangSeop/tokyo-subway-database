package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Line;

import java.util.List;

public interface LineService {

    Line create(LineCreateDto dto);

    /**
     * inquiry method
     */
    Line findById(Long id);
    Line findByNumber(String number);
    List<Line> findAllByCompany(Long companyId);

    List<Line> findAll();

    // creation dto

    public record LineCreateDto(Long companyId, String nameKr, String nameEn, String nameJp, String number) {

        public static LineCreateDto of(Long companyId, String nameKr, String nameEn, String nameJp, String number) {
            return new LineCreateDto(companyId, nameKr, nameEn, nameJp, number);
        }
    }
}
