package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.LineStation;
import com.jangseop.tokyosubwaydatabase.domain.Station;

import java.util.List;

public interface StationService {

    /**
     * create method
     */
    Station create(StationCreateDto dto);

    /**
     * inquiry method
     */
    Station findById(Long id);

    List<Station> findAll();

    /**
     * creation dto
     */
    public record StationCreateDto(String nameKr, String nameEn, String nameJp) {
        public static StationCreateDto of(String nameKr, String nameEn, String nameJp) {
            return new StationCreateDto(nameKr, nameEn, nameJp);
        }
    }
}
