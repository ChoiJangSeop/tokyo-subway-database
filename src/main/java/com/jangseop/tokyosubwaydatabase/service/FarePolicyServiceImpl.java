package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Distance;
import com.jangseop.tokyosubwaydatabase.domain.FarePolicy;
import com.jangseop.tokyosubwaydatabase.entity.FarePolicyEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import com.jangseop.tokyosubwaydatabase.exception.illegal_format.DistanceRangeOverlapException;
import com.jangseop.tokyosubwaydatabase.exception.not_found.FarePolicyNotFoundException;
import com.jangseop.tokyosubwaydatabase.exception.illegal_format.IllegalDistanceRangeException;
import com.jangseop.tokyosubwaydatabase.exception.illegal_format.IllegalFareException;
import com.jangseop.tokyosubwaydatabase.repository.FarePolicyRepository;
import com.jangseop.tokyosubwaydatabase.repository.LineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.jangseop.tokyosubwaydatabase.exception.illegal_format.DistanceRangeOverlapException.*;

@Service
@RequiredArgsConstructor
public class FarePolicyServiceImpl implements FarePolicyService {

    private final FarePolicyRepository farePolicyRepository;
    private final LineRepository lineRepository;

    @Override
    @Transactional
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
    @Transactional(readOnly = true)
    public FarePolicy findById(Long id) {
        FarePolicyEntity farePolicyEntity = farePolicyRepository.findById(id)
                .orElseThrow(() -> new FarePolicyNotFoundException(id));

        return FarePolicy.of(farePolicyEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FarePolicy> findAllByLine(Long lineId) {
        return farePolicyRepository.findAllByLine(lineId).stream()
                .map(FarePolicy::of)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public int getFare(Long lineId, Double distance) {
        List<Integer> fareList = farePolicyRepository.findAllByLine(lineId).stream()
                .filter((entity) -> entity.getMinDistance() <= distance && distance < entity.getMaxDistance())
                .map(FarePolicyEntity::getFare)
                .toList();

        if (fareList.isEmpty()) return -1;
        return fareList.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public int getTotalFare(Distance... distances) {
        return 0;
    }

    private void validateDistanceFormat(double minDistance, double maxDistance) {
        if (minDistance > maxDistance) {
            throw new IllegalDistanceRangeException(new FareRange(minDistance, maxDistance));
        }
    }

    private void validateUniqueDistanceRange(Long lineId, double minDistance, double maxDistance) {
        long existed = farePolicyRepository.findAllByLine(lineId).stream()
                .filter((entity) ->
                        (entity.getMinDistance() <= minDistance && minDistance < entity.getMaxDistance()) ||
                        (entity.getMinDistance() <= maxDistance && maxDistance < entity.getMaxDistance()))
                .count();

        if (existed > 0) {
            throw new DistanceRangeOverlapException(new FareRange(minDistance, maxDistance));
        }
    }

    private void validateFareFormat(int fare) {
        if (fare < 0) {
            throw new IllegalFareException(fare);
        }
    }
}
