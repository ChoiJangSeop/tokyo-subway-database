package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Line;
import com.jangseop.tokyosubwaydatabase.util.createdto.LineCreateDto;

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
}
