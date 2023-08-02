package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.LineStation;
import com.jangseop.tokyosubwaydatabase.repository.LineRepository;
import com.jangseop.tokyosubwaydatabase.repository.LineStationRepository;
import com.jangseop.tokyosubwaydatabase.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LineStationServiceImpl implements LineStationService {

    private final LineStationRepository lineStationRepository;

    private final LineRepository lineRepository;

    private final StationRepository stationRepository;


    @Override
    public LineStation create(String name, Long lineId, Long stationId, double distance) {
        return null;
    }

    @Override
    public LineStation findById(Long id) {
        return null;
    }

    @Override
    public List<LineStation> findAllByLine(Long lineId) {
        return null;
    }

    @Override
    public List<LineStation> findAllByStation(Long stationId) {
        return null;
    }
}
