package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Station;
import com.jangseop.tokyosubwaydatabase.entity.LineStationEntity;
import com.jangseop.tokyosubwaydatabase.entity.StationEntity;
import com.jangseop.tokyosubwaydatabase.repository.StationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StationServiceTest {

    @Test
    @DisplayName("역 정보를 조회합니다 (아이디)")
    public void findById() throws Exception {
        // given
        StationEntity stationEntity = mock(StationEntity.class);

        Long testStationId = 1L;
        String testNameKr = "도쿄역";
        String testNameEn = "Tokyo Station";
        String testNameJp = "東京駅";

        when(stationEntity.getId()).thenReturn(testStationId);
        when(stationEntity.getNameKr()).thenReturn(testNameKr);
        when(stationEntity.getNameEn()).thenReturn(testNameEn);
        when(stationEntity.getNameJp()).thenReturn(testNameJp);

        StationRepository stationRepository = mock(StationRepository.class);

        when(stationRepository.findById(testStationId)).thenReturn(Optional.of(stationEntity));

        // when
        StationServiceImpl stationService = new StationServiceImpl(stationRepository);
        Station findStation = stationService.findById(testStationId);

        // then
        assertThat(findStation.id()).isEqualTo(testStationId);
        assertThat(findStation.nameKr()).isEqualTo(testNameKr);
        assertThat(findStation.nameEn()).isEqualTo(testNameEn);
        assertThat(findStation.nameJp()).isEqualTo(testNameJp);
    }

    @Test
    @DisplayName("역 정보를 조회합니다 (역이름)")
    public void findByNameJp() throws Exception {
        // given
        StationEntity stationEntity = mock(StationEntity.class);

        Long testStationId = 1L;
        String testNameKr = "도쿄역";
        String testNameEn = "Tokyo Station";
        String testNameJp = "東京駅";

        when(stationEntity.getId()).thenReturn(testStationId);
        when(stationEntity.getNameKr()).thenReturn(testNameKr);
        when(stationEntity.getNameEn()).thenReturn(testNameEn);
        when(stationEntity.getNameJp()).thenReturn(testNameJp);

        StationRepository stationRepository = mock(StationRepository.class);
        when(stationRepository.findByNameJp(testNameJp)).thenReturn(List.of(stationEntity));

        // when
        StationService stationService = new StationServiceImpl(stationRepository);
        List<Station> stations = stationService.findByNameJp(testNameJp);

        // then
        assertThat(stations.size()).isEqualTo(1);
        assertThat(stations.get(0).id()).isEqualTo(testStationId);
        assertThat(stations.get(0).nameKr()).isEqualTo(testNameKr);
        assertThat(stations.get(0).nameEn()).isEqualTo(testNameEn);
        assertThat(stations.get(0).nameJp()).isEqualTo(testNameJp);
    }

    @Test
    @DisplayName("모든 역을 조회합니다")
    public void findAll() throws Exception {
        // given
        StationEntity stationEntity = mock(StationEntity.class);

        Long testStationId = 1L;
        String testNameKr = "도쿄역";
        String testNameEn = "Tokyo Station";
        String testNameJp = "東京駅";

        when(stationEntity.getId()).thenReturn(testStationId);
        when(stationEntity.getNameKr()).thenReturn(testNameKr);
        when(stationEntity.getNameEn()).thenReturn(testNameEn);
        when(stationEntity.getNameJp()).thenReturn(testNameJp);

        StationRepository stationRepository = mock(StationRepository.class);
        when(stationRepository.findAll()).thenReturn(List.of(stationEntity));

        // when
        StationService stationService = new StationServiceImpl(stationRepository);
        List<Station> stations = stationService.findAll();

        // then
        assertThat(stations.size()).isEqualTo(1);
        assertThat(stations.get(0).id()).isEqualTo(testStationId);
        assertThat(stations.get(0).nameKr()).isEqualTo(testNameKr);
        assertThat(stations.get(0).nameEn()).isEqualTo(testNameEn);
        assertThat(stations.get(0).nameJp()).isEqualTo(testNameJp);
    }

}