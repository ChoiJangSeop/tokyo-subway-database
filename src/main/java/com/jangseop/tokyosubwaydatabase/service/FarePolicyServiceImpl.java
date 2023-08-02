package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Distance;
import com.jangseop.tokyosubwaydatabase.domain.FarePolicy;
import com.jangseop.tokyosubwaydatabase.entity.FarePolicyEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import com.jangseop.tokyosubwaydatabase.exception.DistanceRangeOverlapException;
import com.jangseop.tokyosubwaydatabase.exception.FarePolicyNotFoundException;
import com.jangseop.tokyosubwaydatabase.exception.IllegalDistanceRangeException;
import com.jangseop.tokyosubwaydatabase.exception.IllegalFareException;
import com.jangseop.tokyosubwaydatabase.repository.FarePolicyRepository;
import com.jangseop.tokyosubwaydatabase.repository.LineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FarePolicyServiceImpl implements FarePolicyService {

    private final FarePolicyRepository farePolicyRepository;
    private final LineRepository lineRepository;

    @Override
    public FarePolicy create(Long lineId, Double minDistance, Double maxDistance, int fare) {

        validateDistanceFormat(minDistance, maxDistance);
        validateFareFormat(fare);
        validateUniqueDistanceRange(lineId, minDistance, maxDistance);

        FarePolicyEntity newFarePolicy = FarePolicyEntity.of(minDistance, maxDistance, fare);

        LineEntity lineEntity = lineRepository.getReferenceById(lineId);
        newFarePolicy.setLine(lineEntity);

        farePolicyRepository.save(newFarePolicy);

        return FarePolicy.of(newFarePolicy);
    }

    @Override
    public FarePolicy findById(Long id) {
        FarePolicyEntity farePolicyEntity = farePolicyRepository.findById(id)
                .orElseThrow(() -> new FarePolicyNotFoundException(id));

        return FarePolicy.of(farePolicyEntity);
    }

    @Override
    public List<FarePolicy> findAllByLine(Long lineId) {
        return farePolicyRepository.findAllByLine(lineId).stream()
                .map(FarePolicy::of)
                .toList();
    }

    @Override
    public int getFare(Long lineId, Double distance) {
        List<Integer> fareList = farePolicyRepository.findAllByLine(lineId).stream()
                .filter((entity) -> entity.getMinDistance() <= distance && distance < entity.getMaxDistance())
                .map(FarePolicyEntity::getFare)
                .toList();

        if (fareList.isEmpty()) return -1;
        return fareList.get(0);
    }

    @Override
    public int getTotalFare(Distance... distances) {
        return 0;
    }

    private void validateDistanceFormat(double minDistance, double maxDistance) {
        if (minDistance > maxDistance) {
            throw new IllegalDistanceRangeException();
        }
    }

    private void validateUniqueDistanceRange(Long lineId, double minDistance, double maxDistance) {
        long existed = farePolicyRepository.findAllByLine(lineId).stream()
                .filter((entity) ->
                        (entity.getMinDistance() <= minDistance && minDistance < entity.getMaxDistance()) ||
                        (entity.getMinDistance() <= maxDistance && maxDistance < entity.getMaxDistance()))
                .count();

        if (existed > 0) {
            throw new DistanceRangeOverlapException(minDistance, maxDistance);
        }
    }

    private void validateFareFormat(int fare) {
        if (fare < 0) {
            throw new IllegalFareException();
        }
    }
}
