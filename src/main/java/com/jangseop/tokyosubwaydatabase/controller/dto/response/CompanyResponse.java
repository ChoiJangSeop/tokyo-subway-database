package com.jangseop.tokyosubwaydatabase.controller.dto.response;

import com.jangseop.tokyosubwaydatabase.domain.Company;

public record CompanyResponse(Long id, String name) {

    public static CompanyResponse of(Company company) {
        return new CompanyResponse(company.id(), company.name());
    }
}
