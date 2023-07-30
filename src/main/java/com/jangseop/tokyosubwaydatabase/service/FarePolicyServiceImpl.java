package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Distance;
import com.jangseop.tokyosubwaydatabase.domain.FarePolicy;
import com.jangseop.tokyosubwaydatabase.repository.FarePolicyRepository;
import com.jangseop.tokyosubwaydatabase.repository.LineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FarePolicyServiceImpl implements FarePolicyService {

    private final FarePolicyRepository farePolicyRepository;
    private final LineRepository lineRepository;

    @Override
    public FarePolicy create(Long lineId, Double minDistance, Double maxDistance, int fare) {
        return null;
    }

    @Override
    public FarePolicy findById(Long id) {
        return null;
    }

    @Override
    public List<FarePolicy> findAllByLine(Long lineId) {
        return null;
    }

    @Override
    public int getFare(Long lineId, Double distance) {
        return 0;
    }

    @Override
    public int getTotalFare(Distance... distances) {
        return 0;
    }
}
