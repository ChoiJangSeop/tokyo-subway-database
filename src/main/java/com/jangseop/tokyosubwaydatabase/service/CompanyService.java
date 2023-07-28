package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Company;
import com.jangseop.tokyosubwaydatabase.domain.Line;

public interface CompanyService {

    /**
     * create method
     */
    Company create(String name);


    /**
     * inquiry method
     */
    Company findById(Long id);
    Company findByName(String name);
}
