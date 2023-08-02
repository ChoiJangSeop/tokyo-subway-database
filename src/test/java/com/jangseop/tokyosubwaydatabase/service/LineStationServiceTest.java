package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.LineStation;
import com.jangseop.tokyosubwaydatabase.domain.TimeUnit;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineStationEntity;
import com.jangseop.tokyosubwaydatabase.entity.StationEntity;
import com.jangseop.tokyosubwaydatabase.exception.IllegalLineStationNameStateException;
import com.jangseop.tokyosubwaydatabase.exception.NoUniqueLineStationException;
import com.jangseop.tokyosubwaydatabase.repository.LineRepository;
import com.jangseop.tokyosubwaydatabase.repository.LineStationRepository;
import com.jangseop.tokyosubwaydatabase.repository.StationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LineStationServiceTest {

    @Test
    @DisplayName("노선 id와 역 id가 동일한 노선역이 존재한다면 예외를 던집니다")
    public void noUniqueLineStationException() throws Exception {
        // given
        LineStationEntity lineStationEntity = mock(LineStationEntity.class);

        String testName = "T01";
        Long testLineId = 1L;
        Long testStationId = 2L;
        double testDistance = 1.0;


        LineStationRepository lineStationRepository = mock(LineStationRepository.class);
        LineRepository lineRepository = mock(LineRepository.class);
        StationRepository stationRepository = mock(StationRepository.class);

        when(lineStationRepository.findAllByLine(testLineId)).thenReturn(List.of(lineStationEntity));
        when(lineStationRepository.findAllByStation(testStationId)).thenReturn(List.of(lineStationEntity));

        // when
        LineStationService lineStationService = new LineStationServiceImpl(lineStationRepository, lineRepository, stationRepository);

        // then
        assertThatThrownBy(() -> lineStationService.create(testName, testLineId, testStationId, testDistance))
                .isInstanceOf(NoUniqueLineStationException.class);
    }



    @Test
    @DisplayName("노선역 기호의 규칙이 잘못되면 예외를 던집니다 (잘못된 형식)")
    public void illegalNameStateException_illegalFormat() throws Exception {

        // given
        String testName = "T0S1";
        Long testLineId = 1L;
        String testLineNumber = "T";
        Long testStationId = 2L;
        double testDistance = 1.0;

        LineStationRepository lineStationRepository = mock(LineStationRepository.class);
        LineRepository lineRepository = mock(LineRepository.class);
        StationRepository stationRepository = mock(StationRepository.class);

        // when
        LineStationService lineStationService = new LineStationServiceImpl(lineStationRepository, lineRepository, stationRepository);

        // then
        assertThatThrownBy(() -> lineStationService.create(testName, testLineId, testStationId, testDistance))
                .isInstanceOf(IllegalLineStationNameStateException.class);
    }

    @Test
    @DisplayName("노선역 기호의 규칙이 잘못되면 예외를 던집니다 (잘못된 노선명)")
    public void illegalNameStateException_illegalLineNumber() throws Exception {
        // given
        String testName = "T01";
        Long testLineId = 1L;
        String testLineNumber = "T";
        Long testStationId = 2L;
        double testDistance = 1.0;

        LineStationRepository lineStationRepository = mock(LineStationRepository.class);
        LineRepository lineRepository = mock(LineRepository.class);
        StationRepository stationRepository = mock(StationRepository.class);

        when(lineRepository.findByNumber(testLineNumber)).thenReturn(Optional.empty());

        // when
        LineStationService lineStationService = new LineStationServiceImpl(lineStationRepository, lineRepository, stationRepository);

        // then
        assertThatThrownBy(() -> lineStationService.create(testName, testLineId, testStationId, testDistance))
                .isInstanceOf(IllegalLineStationNameStateException.class);
    }


    @Test
    public void findById() throws Exception {
        // given
        LineStationEntity lineStationEntity = mock(LineStationEntity.class);
        LineEntity lineEntity = mock(LineEntity.class);
        StationEntity stationEntity = mock(StationEntity.class);

        Long testLineStationId = 1L;
        String testNumber = "T01";
        Long testLineId = 2L;
        Long testStationId = 3L;
        double testDistance = 1.0;

        when(lineStationEntity.getId()).thenReturn(testLineStationId);
        when(lineEntity.getId()).thenReturn(testLineId);
        when(stationEntity.getId()).thenReturn(testStationId);

        when(lineStationEntity.getNumber()).thenReturn(testNumber);
        when(lineStationEntity.getLine()).thenReturn(lineEntity);
        when(lineStationEntity.getStation()).thenReturn(stationEntity);
        when(lineStationEntity.getTimeTable()).thenReturn(List.of());
        when(lineStationEntity.getDistance()).thenReturn(testDistance);


        LineStationRepository lineStationRepository = mock(LineStationRepository.class);
        LineRepository lineRepository = mock(LineRepository.class);
        StationRepository stationRepository = mock(StationRepository.class);

        when(lineStationRepository.findById(testLineStationId)).thenReturn(Optional.of(lineStationEntity));

        // when
        LineStationService lineStationService = new LineStationServiceImpl(lineStationRepository, lineRepository, stationRepository);
        LineStation findLineStation = lineStationService.findById(testLineStationId);

        // then
        assertThat(findLineStation.id()).isEqualTo(testLineId);
        assertThat(findLineStation.lineId()).isEqualTo(testLineId);
        assertThat(findLineStation.stationId()).isEqualTo(testStationId);
        assertThat(findLineStation.number()).isEqualTo(testNumber);
        assertThat(findLineStation.distance()).isEqualTo(testDistance);
    }

    @Test
    public void findAllByLine() throws Exception {
        // given
        LineStationEntity lineStationEntity = mock(LineStationEntity.class);
        LineEntity lineEntity = mock(LineEntity.class);
        StationEntity stationEntity = mock(StationEntity.class);

        Long testLineStationId = 1L;
        String testNumber = "T01";
        Long testLineId = 2L;
        Long testStationId = 3L;
        double testDistance = 1.0;

        when(lineStationEntity.getId()).thenReturn(testLineStationId);
        when(lineEntity.getId()).thenReturn(testLineId);
        when(stationEntity.getId()).thenReturn(testStationId);

        when(lineStationEntity.getNumber()).thenReturn(testNumber);
        when(lineStationEntity.getLine()).thenReturn(lineEntity);
        when(lineStationEntity.getStation()).thenReturn(stationEntity);
        when(lineStationEntity.getTimeTable()).thenReturn(List.of());
        when(lineStationEntity.getDistance()).thenReturn(testDistance);


        LineStationRepository lineStationRepository = mock(LineStationRepository.class);
        LineRepository lineRepository = mock(LineRepository.class);
        StationRepository stationRepository = mock(StationRepository.class);

        when(lineStationRepository.findAllByLine(testLineId)).thenReturn(List.of(lineStationEntity));

        // when
        LineStationService lineStationService = new LineStationServiceImpl(lineStationRepository, lineRepository, stationRepository);
        List<LineStation> findLineStations = lineStationService.findAllByLine(testLineId);

        // then
        assertThat(findLineStations.size()).isEqualTo(1);
        assertThat(findLineStations.get(0).id()).isEqualTo(testLineId);
        assertThat(findLineStations.get(0).lineId()).isEqualTo(testLineId);
        assertThat(findLineStations.get(0).stationId()).isEqualTo(testStationId);
        assertThat(findLineStations.get(0).number()).isEqualTo(testNumber);
        assertThat(findLineStations.get(0).distance()).isEqualTo(testDistance);
    }

    @Test
    public void findAllByStation() throws Exception {
        // given
        LineStationEntity lineStationEntity = mock(LineStationEntity.class);
        LineEntity lineEntity = mock(LineEntity.class);
        StationEntity stationEntity = mock(StationEntity.class);

        Long testLineStationId = 1L;
        String testNumber = "T01";
        Long testLineId = 2L;
        Long testStationId = 3L;
        double testDistance = 1.0;

        when(lineStationEntity.getId()).thenReturn(testLineStationId);
        when(lineEntity.getId()).thenReturn(testLineId);
        when(stationEntity.getId()).thenReturn(testStationId);

        when(lineStationEntity.getNumber()).thenReturn(testNumber);
        when(lineStationEntity.getLine()).thenReturn(lineEntity);
        when(lineStationEntity.getStation()).thenReturn(stationEntity);
        when(lineStationEntity.getTimeTable()).thenReturn(List.of());
        when(lineStationEntity.getDistance()).thenReturn(testDistance);


        LineStationRepository lineStationRepository = mock(LineStationRepository.class);
        LineRepository lineRepository = mock(LineRepository.class);
        StationRepository stationRepository = mock(StationRepository.class);

        when(lineStationRepository.findAllByStation(testStationId)).thenReturn(List.of(lineStationEntity));

        // when
        LineStationService lineStationService = new LineStationServiceImpl(lineStationRepository, lineRepository, stationRepository);
        List<LineStation> findLineStations = lineStationService.findAllByStation(testStationId);

        // then
        assertThat(findLineStations.size()).isEqualTo(1);
        assertThat(findLineStations.get(0).id()).isEqualTo(testLineId);
        assertThat(findLineStations.get(0).lineId()).isEqualTo(testLineId);
        assertThat(findLineStations.get(0).stationId()).isEqualTo(testStationId);
        assertThat(findLineStations.get(0).number()).isEqualTo(testNumber);
        assertThat(findLineStations.get(0).distance()).isEqualTo(testDistance);
    }

}