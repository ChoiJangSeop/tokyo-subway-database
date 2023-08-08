package com.jangseop.tokyosubwaydatabase.controller;

import com.jangseop.tokyosubwaydatabase.controller.dto.StationLineListResponse;
import com.jangseop.tokyosubwaydatabase.controller.dto.StationLineResponse;
import com.jangseop.tokyosubwaydatabase.controller.dto.StationListResponse;
import com.jangseop.tokyosubwaydatabase.controller.dto.StationResponse;
import com.jangseop.tokyosubwaydatabase.domain.Company;
import com.jangseop.tokyosubwaydatabase.domain.Line;
import com.jangseop.tokyosubwaydatabase.domain.LineStation;
import com.jangseop.tokyosubwaydatabase.domain.Station;
import com.jangseop.tokyosubwaydatabase.exception.duplicated.LineStationDuplicatedException;
import com.jangseop.tokyosubwaydatabase.exception.not_found.LineStationNotFoundException;
import com.jangseop.tokyosubwaydatabase.service.CompanyService;
import com.jangseop.tokyosubwaydatabase.service.LineService;
import com.jangseop.tokyosubwaydatabase.service.LineStationService;
import com.jangseop.tokyosubwaydatabase.service.StationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.jangseop.tokyosubwaydatabase.exception.duplicated.LineStationDuplicatedException.*;

@AllArgsConstructor
@RestController
@RequestMapping
public class StationController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private final StationService stationService;

    private final LineStationService lineStationService;

    private final CompanyService companyService;

    private final LineService lineService;

    @GetMapping("/stations")
    public ResponseEntity<StationListResponse> allStations() {
        List<StationResponse> stationResponses = stationService.findAll().stream()
                .map(StationResponse::of)
                .toList();

        return new ResponseEntity<>(new StationListResponse(stationResponses), HttpStatus.OK);
    }

    @GetMapping("/stations/{stationId}")
    public ResponseEntity<StationResponse> oneStation(@PathVariable Long stationId) {
        Station station = stationService.findById(stationId);
        return new ResponseEntity<>(StationResponse.of(station), HttpStatus.OK);
    }

    @GetMapping("/stations/{stationId}/lines")
    public ResponseEntity<StationLineListResponse> allLines(@PathVariable Long stationId) {
        List<StationLineResponse> stationLineResponses = lineStationService.findAllByStation(stationId).stream()
                .map(lineStation -> StationLineResponse.of(
                        lineStation,
                        lineService.findById(lineStation.lineId()),
                        companyService.findById(lineService.findById(lineStation.lineId()).companyId())
                )).toList();

        return new ResponseEntity<>(new StationLineListResponse(stationLineResponses), HttpStatus.OK);
    }

    @GetMapping("/stations/{stationId}/lines/{lineId}")
    public ResponseEntity<StationLineResponse> oneLine(@PathVariable Long stationId, @PathVariable Long lineId) {
        List<LineStation> lineStations = lineStationService.findAllByStation(stationId).stream()
                .filter(lineStation -> Objects.equals(lineStation.lineId(), lineId))
                .toList();

        if (lineStations.isEmpty()) throw new LineStationNotFoundException(new LineStationIdentifier(lineId, stationId));

        LineStation lineStation = lineStations.get(0);
        Line line = lineService.findById(lineStation.lineId());
        Company company = companyService.findById(line.companyId());

        return new ResponseEntity<>(StationLineResponse.of(lineStation, line, company), HttpStatus.OK);
    }

}
