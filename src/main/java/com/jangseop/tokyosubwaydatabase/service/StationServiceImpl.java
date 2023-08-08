package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Station;
import com.jangseop.tokyosubwaydatabase.entity.StationEntity;
import com.jangseop.tokyosubwaydatabase.exception.StationNotFoundException;
import com.jangseop.tokyosubwaydatabase.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    @Override
    public Station create(String nameKr, String nameEn, String nameJp) {

        StationEntity stationEntity = StationEntity.of(nameKr, nameEn, nameJp);

        stationRepository.save(stationEntity);

        return Station.of(stationEntity);
    }

    @Override
    public Station findById(Long id) {

        StationEntity stationEntity = stationRepository.findById(id)
                .orElseThrow(() -> new StationNotFoundException(id));

        return Station.of(stationEntity);
    }

    @Override
    public List<Station> findAll() {
        return stationRepository.findAll().stream()
                .map(Station::of)
                .toList();
    }
}
