package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.LineStation;
import com.jangseop.tokyosubwaydatabase.domain.Station;

import java.util.List;

public interface StationService {

    /**
     * create method
     */
    Station create(String nameKr, String nameEn, String nameJp);

    /**
     * inquiry method
     */
    Station findById(Long id);

    List<Station> findAll();
}
