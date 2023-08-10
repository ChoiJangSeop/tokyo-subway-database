package com.jangseop.tokyosubwaydatabase.util.create_dto;

import com.jangseop.tokyosubwaydatabase.service.CompanyService;

/**
 * create dto
 */
public record CompanyCreateDto(String name) {
    public static CompanyCreateDto of(String name) {
        return new CompanyCreateDto(name);
    }
}
