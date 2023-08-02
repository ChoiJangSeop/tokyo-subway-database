package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Station;
import com.jangseop.tokyosubwaydatabase.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    @Override
    public Station create(String nameKr, String nameEn, String nameJp) {
        return null;
    }

    @Override
    public Station findById(Long id) {
        return null;
    }
}
