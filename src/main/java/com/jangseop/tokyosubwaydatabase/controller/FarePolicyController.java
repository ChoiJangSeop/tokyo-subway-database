package com.jangseop.tokyosubwaydatabase.controller;

import com.jangseop.tokyosubwaydatabase.controller.dto.request.FarePolicyCreateRequest;
import com.jangseop.tokyosubwaydatabase.controller.dto.response.FarePolicyResponse;
import com.jangseop.tokyosubwaydatabase.domain.FarePolicy;
import com.jangseop.tokyosubwaydatabase.exception.illegalformat.DistanceRangeOverlapException;
import com.jangseop.tokyosubwaydatabase.exception.illegalformat.IllegalDistanceRangeException;
import com.jangseop.tokyosubwaydatabase.exception.illegalformat.IllegalFareException;
import com.jangseop.tokyosubwaydatabase.service.FarePolicyService;
import com.jangseop.tokyosubwaydatabase.util.createdto.FarePolicyCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
@CrossOrigin(origins = "http://localhost:8081")
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
