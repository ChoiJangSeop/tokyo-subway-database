package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Station;
import com.jangseop.tokyosubwaydatabase.util.createdto.StationCreateDto;

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

    List<Station> findByNameJp(String nameJp);
}
