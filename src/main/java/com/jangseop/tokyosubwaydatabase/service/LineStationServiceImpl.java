package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.LineStation;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineStationEntity;
import com.jangseop.tokyosubwaydatabase.entity.StationEntity;
import com.jangseop.tokyosubwaydatabase.exception.IllegalLineStationNameStateException;
import com.jangseop.tokyosubwaydatabase.exception.LineStationNotFoundException;
import com.jangseop.tokyosubwaydatabase.exception.NoUniqueLineStationException;
import com.jangseop.tokyosubwaydatabase.repository.LineRepository;
import com.jangseop.tokyosubwaydatabase.repository.LineStationRepository;
import com.jangseop.tokyosubwaydatabase.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class LineStationServiceImpl implements LineStationService {

    private final LineStationRepository lineStationRepository;

    private final LineRepository lineRepository;

    private final StationRepository stationRepository;


    @Override
    public LineStation create(String name, Long lineId, Long stationId, double distance) {

        validateLineStationNumberFormat(name);
        validateUniqueLineStation(lineId, stationId);

        LineEntity lineEntity = lineRepository.getReferenceById(lineId);
        StationEntity stationEntity = stationRepository.getReferenceById(stationId);

        LineStationEntity newLineStationEntity = LineStationEntity.of(name, distance);
        newLineStationEntity.setLine(lineEntity);
        newLineStationEntity.setStation(stationEntity);

        lineStationRepository.save(newLineStationEntity);

        return LineStation.of(newLineStationEntity);
    }

    @Override
    public LineStation findById(Long id) {

        LineStationEntity entity = lineStationRepository.findById(id)
                .orElseThrow(() -> new LineStationNotFoundException(id));

        return LineStation.of(entity);
    }

    @Override
    public List<LineStation> findAllByLine(Long lineId) {
        return lineStationRepository.findAllByLine(lineId).stream()
                .map(LineStation::of)
                .toList();
    }

    @Override
    public List<LineStation> findAllByStation(Long stationId) {
        return lineStationRepository.findAllByStation(stationId).stream()
                .map(LineStation::of)
                .toList();
    }

    private void validateUniqueLineStation(Long lineId, Long stationId) {

        long count = lineStationRepository.findAllByLine(lineId).stream()
                .filter((entity) -> entity.getStation().getId().equals(stationId))
                .count();

        if (count > 0) {
            throw new NoUniqueLineStationException(lineId, stationId);
        }
    }

    private void validateLineStationNumberFormat(String number) {

        for (int index=1; index<number.length(); ++index) {
            String lineNumber = number.substring(0, index);
            String stationOrder = number.substring(index);

            String onlyNumbers = "^[0-9]*$";
            String onlyEns = "^[A-Z]*$";

            if (Pattern.matches(onlyEns, lineNumber) && Pattern.matches(onlyNumbers, stationOrder)) {

                if (lineRepository.findByNumber(lineNumber).isEmpty()) {
                    throw new IllegalLineStationNameStateException(lineNumber);
                }

                return;
            }
        }

        throw new IllegalLineStationNameStateException();
    }
}
