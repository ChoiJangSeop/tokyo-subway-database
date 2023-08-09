package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.LineStation;
import com.jangseop.tokyosubwaydatabase.domain.LineStationIdentifier;

import java.util.List;

import static com.jangseop.tokyosubwaydatabase.service.LineStationServiceImpl.*;

public interface LineStationService {

    /**
     * create method
     */
//    LineStation create(String name, Long lineId, Long stationId, double distance);

    // 생생 메서드 파라미터 -> 레코드
    LineStation create(LineStationCreateDto lineStationDto);

    /**
     * inquiry method
     */
    LineStation findById(Long id);

    List<LineStation> findAllByLine(Long lineId);

    List<LineStation> findAllByStation(Long stationId);

    LineStation findByIdentifier(LineStationIdentifier identifier);

    // TODO method for route search

}
