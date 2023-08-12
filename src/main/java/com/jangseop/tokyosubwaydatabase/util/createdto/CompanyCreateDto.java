package com.jangseop.tokyosubwaydatabase.util.createdto;

/**
 * create dto
 */
public record CompanyCreateDto(String name) {
    public static CompanyCreateDto of(String name) {
        return new CompanyCreateDto(name);
    }
}
