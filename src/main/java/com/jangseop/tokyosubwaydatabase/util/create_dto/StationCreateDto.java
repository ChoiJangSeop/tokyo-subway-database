package com.jangseop.tokyosubwaydatabase.util.create_dto;

import com.jangseop.tokyosubwaydatabase.service.StationService;

/**
 * creation dto
 */
public record StationCreateDto(String nameKr, String nameEn, String nameJp) {
    public static StationCreateDto of(String nameKr, String nameEn, String nameJp) {
        return new StationCreateDto(nameKr, nameEn, nameJp);
    }
}
