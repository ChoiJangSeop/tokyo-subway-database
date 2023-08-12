package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Distance;
import com.jangseop.tokyosubwaydatabase.domain.FarePolicy;
import com.jangseop.tokyosubwaydatabase.util.createdto.FarePolicyCreateDto;

import java.util.List;

public interface FarePolicyService {

    /**
     * create method
     */
    FarePolicy create(FarePolicyCreateDto dto);

    /**
     * inquiry method
     */
    FarePolicy findById(Long id);

    List<FarePolicy> findAllByLine(Long lineId);

    int getFare(Long lineId, Double distance);

    int getTotalFare(Distance... distances);
}
