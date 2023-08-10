package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.FarePolicy;
import com.jangseop.tokyosubwaydatabase.entity.FarePolicyEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import com.jangseop.tokyosubwaydatabase.exception.illegal_format.DistanceRangeOverlapException;
import com.jangseop.tokyosubwaydatabase.exception.illegal_format.IllegalDistanceRangeException;
import com.jangseop.tokyosubwaydatabase.exception.illegal_format.IllegalFareException;
import com.jangseop.tokyosubwaydatabase.repository.FarePolicyRepository;
import com.jangseop.tokyosubwaydatabase.repository.LineRepository;
import com.jangseop.tokyosubwaydatabase.util.create_dto.FarePolicyCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FarePolicyServiceTest {

    /**
     * 생성 관련 예외
     * 1. 생성 범위가 겹치는 경우
     * 2. 생성 범위 설정이 잘못된 경우 (최대 <= 최소, 최대,최소 < 0)
     * 3. 가격이 잘못된 경우 (가격 < 0)
     */
    @DisplayName("기존 요금정책과 거리범위가 겹칠 경우, 예외를 던진다")
    @Test
    public void rangeOverlapException() throws Exception {
        // given
        FarePolicyEntity farePolicyEntity = mock(FarePolicyEntity.class);

        Long testLineId = 1L;
        Double testMinDistance = 0.0;
        Double testMaxDistance = 10.0;

        when(farePolicyEntity.getMinDistance()).thenReturn(testMinDistance);
        when(farePolicyEntity.getMaxDistance()).thenReturn(testMaxDistance);

        FarePolicyRepository farePolicyRepository = mock(FarePolicyRepository.class);
        LineRepository lineRepository = mock(LineRepository.class);
        when(farePolicyRepository.findAllByLine(lineRepository.getReferenceById(testLineId))).thenReturn(List.of(farePolicyEntity));

        FarePolicyService fareService = new FarePolicyServiceImpl(farePolicyRepository, lineRepository);

        Double testNewMinDistance = 9.0;
        Double testNewMaxDistance = 20.0;
        int testNewFare = 2;

        // then
        assertThatThrownBy(() -> fareService.create(FarePolicyCreateDto.of(testLineId, testNewMinDistance, testNewMaxDistance, testNewFare)))
                .isInstanceOf(DistanceRangeOverlapException.class);
    }

    @DisplayName("잘못된 거리가 입력될 경우, 예외를 던진다")
    @Test
    public void illegalDistanceException() throws Exception {
        // given
        Long testLineId = 1L;
        Double testMinDistance = 10.0;
        Double testMaxDistance = 0.0;
        int testFare = 1;

        LineRepository lineRepository = mock(LineRepository.class);
        FarePolicyRepository farePolicyRepository = mock(FarePolicyRepository.class);

        // when
        FarePolicyService farePolicyService = new FarePolicyServiceImpl(farePolicyRepository, lineRepository);

        // then
        assertThatThrownBy(() -> farePolicyService.create(FarePolicyCreateDto.of(testLineId, testMinDistance, testMaxDistance, testFare)))
                .isInstanceOf(IllegalDistanceRangeException.class);
    }

    @DisplayName("잘못된 요금이 입력될 경우, 예외를 던진다")
    @Test
    public void illegalFareException() throws Exception {
        // given
        Long testLineId = 1L;
        Double testMinDistance = 0.0;
        Double testMaxDistance = 10.0;
        int testFare = -1;

        LineRepository lineRepository = mock(LineRepository.class);
        FarePolicyRepository farePolicyRepository = mock(FarePolicyRepository.class);

        // when
        FarePolicyService farePolicyService = new FarePolicyServiceImpl(farePolicyRepository, lineRepository);

        // then
        assertThatThrownBy(() -> farePolicyService.create(FarePolicyCreateDto.of(testLineId, testMinDistance, testMaxDistance, testFare)))
                .isInstanceOf(IllegalFareException.class);
    }

    /**
     * 조회 관련 테스트
     */
    @DisplayName("운임정책 정보를 조회한다(아이디)")
    @Test
    public void findById() throws Exception {
        // given
        FarePolicyEntity farePolicyEntity = mock(FarePolicyEntity.class);
        LineEntity lineEntity = mock(LineEntity.class);

        Long testFarePolicyId = 1L;
        Double testMinDistance = 0.0;
        Double testMaxDistance = 10.0;
        int testFare = 1;

        when(farePolicyEntity.getId()).thenReturn(testFarePolicyId);
        when(farePolicyEntity.getLine()).thenReturn(lineEntity);
        when(farePolicyEntity.getMinDistance()).thenReturn(testMinDistance);
        when(farePolicyEntity.getMaxDistance()).thenReturn(testMaxDistance);
        when(farePolicyEntity.getFare()).thenReturn(testFare);

        Long testLineId = 2L;

        when(lineEntity.getId()).thenReturn(testLineId);

        FarePolicyRepository farePolicyRepository = mock(FarePolicyRepository.class);
        LineRepository lineRepository = mock(LineRepository.class);

        when(farePolicyRepository.findById(testFarePolicyId)).thenReturn(Optional.of(farePolicyEntity));

        // when
        FarePolicyService farePolicyService = new FarePolicyServiceImpl(farePolicyRepository, lineRepository);
        FarePolicy farePolicy = farePolicyService.findById(testFarePolicyId);

        // then
        assertThat(farePolicy.id()).isEqualTo(testFarePolicyId);
        assertThat(farePolicy.lineId()).isEqualTo(testLineId);
        assertThat(farePolicy.minDistance()).isEqualTo(testMinDistance);
        assertThat(farePolicy.maxDistance()).isEqualTo(testMaxDistance);
        assertThat(farePolicy.fare()).isEqualTo(testFare);
    }

    @DisplayName("모든 운임정책 정보를 조회한다(노선)")
    @Test
    public void findAllByLine() throws Exception {
        // given
        FarePolicyEntity farePolicyEntity = mock(FarePolicyEntity.class);
        LineEntity lineEntity = mock(LineEntity.class);

        Long testFarePolicyId = 1L;
        Double testMinDistance = 0.0;
        Double testMaxDistance = 10.0;
        int testFare = 1;

        when(farePolicyEntity.getId()).thenReturn(testFarePolicyId);
        when(farePolicyEntity.getLine()).thenReturn(lineEntity);
        when(farePolicyEntity.getMinDistance()).thenReturn(testMinDistance);
        when(farePolicyEntity.getMaxDistance()).thenReturn(testMaxDistance);
        when(farePolicyEntity.getFare()).thenReturn(testFare);

        Long testLineId = 2L;

        when(lineEntity.getId()).thenReturn(testLineId);

        FarePolicyRepository farePolicyRepository = mock(FarePolicyRepository.class);
        LineRepository lineRepository = mock(LineRepository.class);

        when(farePolicyRepository.findAllByLine(lineRepository.getReferenceById(testLineId))).thenReturn(List.of(farePolicyEntity));

        // when
        FarePolicyService farePolicyService = new FarePolicyServiceImpl(farePolicyRepository, lineRepository);
        List<FarePolicy> farePolicies = farePolicyService.findAllByLine(testLineId);

        // then
        assertThat(farePolicies.size()).isEqualTo(1);
        assertThat(farePolicies.get(0).id()).isEqualTo(testFarePolicyId);
        assertThat(farePolicies.get(0).lineId()).isEqualTo(testLineId);
        assertThat(farePolicies.get(0).minDistance()).isEqualTo(testMinDistance);
        assertThat(farePolicies.get(0).maxDistance()).isEqualTo(testMaxDistance);
        assertThat(farePolicies.get(0).fare()).isEqualTo(testFare);
    }

    /**
     * 비지니스 로직 관련 테스트
     */
    @DisplayName("이동거리와 노선에 따른 요금을 반환한다")
    @Test
    public void getFare() throws Exception {

        // given
        FarePolicyEntity farePolicyEntity = mock(FarePolicyEntity.class);
        LineEntity lineEntity = mock(LineEntity.class);

        Long testLineId = 1L;
        Double testMinDistance = 0.0;
        Double testMaxDistance = 10.0;
        int testFare = 1;

        when(farePolicyEntity.getMinDistance()).thenReturn(testMinDistance);
        when(farePolicyEntity.getMaxDistance()).thenReturn(testMaxDistance);
        when(farePolicyEntity.getFare()).thenReturn(testFare);

        FarePolicyRepository farePolicyRepository = mock(FarePolicyRepository.class);
        LineRepository lineRepository = mock(LineRepository.class);
        when(farePolicyRepository.findAllByLine(lineRepository.getReferenceById(testLineId))).thenReturn(List.of(farePolicyEntity));

        // when
        FarePolicyService fareService = new FarePolicyServiceImpl(farePolicyRepository, lineRepository);
        Double testInputDistance1 = 0.0;
        Double testInputDistance2 = 10.0;
        Double testInputDistance3 = 5.0;
        Double testInputDistance4 = 11.0;

        int fare1 = fareService.getFare(testLineId, testInputDistance1);
        int fare2 = fareService.getFare(testLineId, testInputDistance2);
        int fare3 = fareService.getFare(testLineId, testInputDistance3);
        int fare4 = fareService.getFare(testLineId, testInputDistance4);

        // then
        assertThat(fare1).isEqualTo(testFare);
        assertThat(fare2).isEqualTo(-1);
        assertThat(fare3).isEqualTo(testFare);
        assertThat(fare4).isEqualTo(-1);
    }
}