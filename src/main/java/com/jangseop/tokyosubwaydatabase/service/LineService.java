package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Line;

import java.util.List;

public interface LineService {

    Line create(Long companyId, String nameKr, String nameEn, String nameJp, String number);

    /**
     * inquiry method
     */
    Line findById(Long id);
    Line findByNumber(String number);
    List<Line> findAllByCompany(Long companyId);

    List<Line> findAll();
}
