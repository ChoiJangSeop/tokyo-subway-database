package com.jangseop.tokyosubwaydatabase.controller;

import com.jangseop.tokyosubwaydatabase.controller.dto.request.LineStationCreateRequest;
import com.jangseop.tokyosubwaydatabase.controller.dto.response.line_station.LineStationListResponse;
import com.jangseop.tokyosubwaydatabase.controller.dto.response.line_station.LineStationResponse;
import com.jangseop.tokyosubwaydatabase.domain.Line;
import com.jangseop.tokyosubwaydatabase.domain.LineStation;
import com.jangseop.tokyosubwaydatabase.domain.LineStationIdentifier;
import com.jangseop.tokyosubwaydatabase.domain.Station;
import com.jangseop.tokyosubwaydatabase.exception.illegalformat.IllegalLineStationNameStateException;
import com.jangseop.tokyosubwaydatabase.service.LineService;
import com.jangseop.tokyosubwaydatabase.service.LineStationService;
import com.jangseop.tokyosubwaydatabase.service.StationService;
import com.jangseop.tokyosubwaydatabase.util.createdto.LineStationCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
@CrossOrigin(origins = "http://localhost:8081")
public class LineStationController {

    private final LineStationService lineStationService;

    private final LineService lineService;

    private final StationService stationService;

    @PostMapping("/lineStations")
    public void newOne(@RequestBody LineStationCreateRequest request) {
        validateLineStationNumberFormat(request.number());
        LineStationCreateDto dto = LineStationCreateDto.of(request.number(), request.lineId(), request.stationId(), request.distance());

        lineStationService.create(dto);
    }

    @GetMapping("/lineStations/lines/{lineId}")
    public ResponseEntity<LineStationListResponse> allLineStationsByLine(@PathVariable Long lineId) {
        List<LineStationResponse> lineStations = lineStationService.findAllByLine(lineId).stream()
                .map(lineStation ->
                        LineStationResponse.of(lineStation, lineService.findById(lineStation.lineId()), stationService.findById(lineStation.stationId())))
                .toList();

        return new ResponseEntity<>(LineStationListResponse.of(lineStations), HttpStatus.OK);
    }

    @GetMapping("/lineStations/stations/{stationId}")
    public ResponseEntity<LineStationListResponse> allLineStationByStation(@PathVariable Long stationId) {
        List<LineStationResponse> lineStations = lineStationService.findAllByStation(stationId).stream()
                .map(lineStation ->
                        LineStationResponse.of(lineStation, lineService.findById(lineStation.lineId()), stationService.findById(lineStation.stationId())))
                .toList();

        return new ResponseEntity<>(LineStationListResponse.of(lineStations), HttpStatus.OK);
    }

    @GetMapping("/lineStations/lines/{lineId}/stations/{stationId}")
    public ResponseEntity<LineStationResponse> oneLineStation(@PathVariable Long lineId, @PathVariable Long stationId) {
        LineStation lineStation = lineStationService.findByIdentifier(LineStationIdentifier.of(lineId, stationId));
        Line line = lineService.findById(lineStation.lineId());
        Station station = stationService.findById(lineStation.stationId());

        return new ResponseEntity<>(LineStationResponse.of(lineStation, line, station), HttpStatus.OK);
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
