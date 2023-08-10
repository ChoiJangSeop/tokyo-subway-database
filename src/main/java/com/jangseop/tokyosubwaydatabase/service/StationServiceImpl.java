package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Station;
import com.jangseop.tokyosubwaydatabase.entity.StationEntity;
import com.jangseop.tokyosubwaydatabase.exception.not_found.StationNotFoundException;
import com.jangseop.tokyosubwaydatabase.repository.StationRepository;
import com.jangseop.tokyosubwaydatabase.util.create_dto.StationCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    @Override
    @Transactional
    public Station create(StationCreateDto dto) {

        StationEntity stationEntity = StationEntity.of(dto.nameKr(), dto.nameEn(), dto.nameJp());

        StationEntity newStation = stationRepository.save(stationEntity);

        return Station.of(newStation);
    }

    @Override
    @Transactional(readOnly = true)
    public Station findById(Long id) {

        StationEntity stationEntity = stationRepository.findById(id)
                .orElseThrow(() -> new StationNotFoundException(id));

        return Station.of(stationEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Station> findAll() {
        return stationRepository.findAll().stream()
                .map(Station::of)
                .toList();
    }
}
