package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Company;
import com.jangseop.tokyosubwaydatabase.domain.Line;

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

    /**
     * create dto
     */
    public record CompanyCreateDto(String name) {
        public static CompanyCreateDto of(String name) {
            return new CompanyCreateDto(name);
        }
    }
}
