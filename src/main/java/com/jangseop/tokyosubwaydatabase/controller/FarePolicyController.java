package com.jangseop.tokyosubwaydatabase.controller;

import com.jangseop.tokyosubwaydatabase.controller.dto.request.FarePolicyCreateRequest;
import com.jangseop.tokyosubwaydatabase.controller.dto.response.FarePolicyResponse;
import com.jangseop.tokyosubwaydatabase.domain.FarePolicy;
import com.jangseop.tokyosubwaydatabase.exception.illegal_format.DistanceRangeOverlapException;
import com.jangseop.tokyosubwaydatabase.exception.illegal_format.IllegalDistanceRangeException;
import com.jangseop.tokyosubwaydatabase.exception.illegal_format.IllegalFareException;
import com.jangseop.tokyosubwaydatabase.service.FarePolicyService;
import com.jangseop.tokyosubwaydatabase.util.create_dto.FarePolicyCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
public class FarePolicyController {

    private final FarePolicyService farePolicyService;

    @PostMapping("/farePolicy")
    public ResponseEntity<FarePolicyResponse> newOne(@RequestBody FarePolicyCreateRequest request) {

        validateDistanceFormat(request.minDistance(), request.maxDistance());
        validateFareFormat(request.fare());

        FarePolicyCreateDto dto = FarePolicyCreateDto.of(request.lineId(), request.minDistance(), request.maxDistance(), request.fare());
        FarePolicy newFarePolicy = farePolicyService.create(dto);

        return new ResponseEntity<>(FarePolicyResponse.of(newFarePolicy), HttpStatus.OK);
    }

    private void validateDistanceFormat(double minDistance, double maxDistance) {
        if (minDistance > maxDistance) {
            throw new IllegalDistanceRangeException(new DistanceRangeOverlapException.FareRange(minDistance, maxDistance));
        }
    }

    private void validateFareFormat(int fare) {
        if (fare < 0) {
            throw new IllegalFareException(fare);
        }
    }
}
