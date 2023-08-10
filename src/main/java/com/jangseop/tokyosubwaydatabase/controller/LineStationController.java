package com.jangseop.tokyosubwaydatabase.controller;

import com.jangseop.tokyosubwaydatabase.controller.dto.request.LineStationCreateRequest;
import com.jangseop.tokyosubwaydatabase.exception.illegal_format.IllegalLineStationNameStateException;
import com.jangseop.tokyosubwaydatabase.service.LineStationService;
import com.jangseop.tokyosubwaydatabase.util.create_dto.LineStationCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
public class LineStationController {

    private final LineStationService lineStationService;

    @PostMapping("/lineStations")
    public void newOne(@RequestBody LineStationCreateRequest request) {
        validateLineStationNumberFormat(request.number());
        LineStationCreateDto dto = LineStationCreateDto.of(request.number(), request.lineId(), request.stationId(), request.distance());

        lineStationService.create(dto);
    }

    private void validateLineStationNumberFormat(String number) {

        for (int index=1; index<number.length(); ++index) {
            String lineNumber = number.substring(0, index);
            String stationOrder = number.substring(index);

            String onlyNumbers = "^[0-9]*$";
            String onlyEns = "^[A-Z]*$";

            if (Pattern.matches(onlyEns, lineNumber) && Pattern.matches(onlyNumbers, stationOrder)) return;

        }

        throw new IllegalLineStationNameStateException(number);
    }
}
