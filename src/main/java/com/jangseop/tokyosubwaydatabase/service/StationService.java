package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Station;

public interface StationService {

    /**
     * create method
     */
    Station create(String nameKr, String nameEn, String nameJp);

    /**
     * inquiry method
     */
    Station findById(Long id);
}
