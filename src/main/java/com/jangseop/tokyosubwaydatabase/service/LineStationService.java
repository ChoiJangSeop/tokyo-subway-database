package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.LineStation;

import java.util.List;

public interface LineStationService {

    /**
     * create method
     */
    LineStation create(String name, Long lineId, Long stationId, int distance);

    /**
     * inquiry method
     */
    LineStation findById(Long id);

    List<LineStation> findAllByLine(Long lineId);

    List<LineStation> findAllByStation(Long stationId);

}
