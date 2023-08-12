package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.LineStation;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineStationEntity;
import com.jangseop.tokyosubwaydatabase.entity.StationEntity;
import com.jangseop.tokyosubwaydatabase.domain.LineStationIdentifier;
import com.jangseop.tokyosubwaydatabase.exception.illegalformat.IllegalLineStationNameStateException;
import com.jangseop.tokyosubwaydatabase.exception.notfound.LineStationNotFoundException;
import com.jangseop.tokyosubwaydatabase.exception.duplicated.LineStationDuplicatedException;
import com.jangseop.tokyosubwaydatabase.repository.LineRepository;
import com.jangseop.tokyosubwaydatabase.repository.LineStationRepository;
import com.jangseop.tokyosubwaydatabase.repository.StationRepository;
import com.jangseop.tokyosubwaydatabase.util.createdto.LineStationCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class LineStationServiceImpl implements LineStationService {

    private final LineStationRepository lineStationRepository;

    private final LineRepository lineRepository;

    private final StationRepository stationRepository;


    // 생생 메서드 파라미터 -> 레코드
    @Override
    @Transactional
    public LineStation create(LineStationCreateDto dto) {

        validateLineStationNumberFormat(dto.name());
        validateUniqueLineStation(dto.lineId(), dto.stationId());

        LineEntity lineEntity = lineRepository.getReferenceById(dto.lineId());
        StationEntity stationEntity = stationRepository.getReferenceById(dto.stationId());

        LineStationEntity newLineStationEntity = LineStationEntity.of(dto.name(), dto.distance());
        newLineStationEntity.setLine(lineEntity);
        newLineStationEntity.setStation(stationEntity);

        lineStationRepository.save(newLineStationEntity);

        return LineStation.of(newLineStationEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public LineStation findById(Long id) {

        LineStationEntity entity = lineStationRepository.findById(id)
                .orElseThrow(() -> new LineStationNotFoundException(id));

        return LineStation.of(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineStation> findAllByLine(Long lineId) {

        LineEntity line = lineRepository.getReferenceById(lineId);

        return lineStationRepository.findAllByLine(line).stream()
                .map(LineStation::of)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LineStation> findAllByStation(Long stationId) {

        StationEntity station = stationRepository.getReferenceById(stationId);

        return lineStationRepository.findAllByStation(station).stream()
                .map(LineStation::of)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LineStation findByIdentifier(LineStationIdentifier identifier) {

        LineEntity line = lineRepository.getReferenceById(identifier.lineId());

        List<LineStation> lineStations = lineStationRepository.findAllByLine(line).stream()
                .map(LineStation::of)
                .filter(lineStation -> lineStation.stationId().equals(identifier.stationId()))
                .toList();

        if (lineStations.isEmpty()) throw new LineStationNotFoundException(identifier);

        return lineStations.get(0);
    }

    // TODO
    //  도메인 관련 -> service
    //  포멧이나 null checking -> controller


    // Objects.equals -> null checking
    private void validateUniqueLineStation(Long lineId, Long stationId) {

        long count = lineStationRepository.findAllByLine(lineRepository.getReferenceById(lineId)).stream()
                .filter(entity -> Objects.equals(entity.getStation().getId(), stationId))
                .count();

        if (count > 0) {
            throw new LineStationDuplicatedException(new LineStationIdentifier(lineId, stationId));
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
