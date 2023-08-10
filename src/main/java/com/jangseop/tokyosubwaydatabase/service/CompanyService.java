package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Company;
import com.jangseop.tokyosubwaydatabase.util.create_dto.CompanyCreateDto;

public interface CompanyService {

    /**
     * create method
     */
    Company create(CompanyCreateDto dto);


    /**
     * inquiry method
     */
    Company findById(Long id);
    Company findByName(String name);

}
