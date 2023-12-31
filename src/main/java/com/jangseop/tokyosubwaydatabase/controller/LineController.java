package com.jangseop.tokyosubwaydatabase.controller;

import com.jangseop.tokyosubwaydatabase.controller.dto.request.LineCreateRequest;
import com.jangseop.tokyosubwaydatabase.controller.dto.response.*;
import com.jangseop.tokyosubwaydatabase.domain.Company;
import com.jangseop.tokyosubwaydatabase.domain.Line;
import com.jangseop.tokyosubwaydatabase.exception.illegalformat.IllegalLineNumberFormatException;
import com.jangseop.tokyosubwaydatabase.service.*;
import com.jangseop.tokyosubwaydatabase.util.createdto.LineCreateDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;


@AllArgsConstructor
@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:8081")
public class LineController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final LineService lineService;

    private final CompanyService companyService;

    private final LineStationService lineStationService;

    private final StationService stationService;

    private final FarePolicyService farePolicyService;

    @GetMapping("/lines")
    public ResponseEntity<LineListResponse> allLines() {
        List<LineResponse> lines = lineService.findAll().stream()
                .map(line -> LineResponse.of(line, companyService.findById(line.companyId()).name()))
                .toList();

        logger.info("Lines: {}", lines);
        return new ResponseEntity<>(LineListResponse.of(lines), HttpStatus.OK);
    }

    @PostMapping("/lines")
    public ResponseEntity<LineResponse> newOne(@RequestBody LineCreateRequest request) {

        validateLineNumberFormat(request.number());

        LineCreateDto dto = LineCreateDto.of(
                request.companyId(),
                request.nameKr(), request.nameEn(), request.nameJp(),
                request.number());
        Line line = lineService.create(dto);
        Company company = companyService.findById(line.companyId());
        return new ResponseEntity<>(LineResponse.of(line, company.name()), HttpStatus.OK);
    }

    @GetMapping("/lines/{lineId}")
    public ResponseEntity<LineResponse> oneLine(@PathVariable Long lineId) {
        Line line = lineService.findById(lineId);
        String companyName = companyService.findById(line.companyId()).name();

        logger.info("Line: {}", line);
        return new ResponseEntity<>(LineResponse.of(line, companyName), HttpStatus.OK);
    }

    @GetMapping("/lines/{lineId}/stations")
    public ResponseEntity<LineStationListResponseV1> allStations(@PathVariable Long lineId) {
        List<LineStationResponseV1> lineStationResponses = lineStationService.findAllByLine(lineId).stream()
                .map(lineStation -> LineStationResponseV1.of(lineStation, stationService.findById(lineStation.stationId())))
                .toList();

        logger.info("line id: {}, stations: {}", lineId, lineStationResponses);
        return new ResponseEntity<>(LineStationListResponseV1.of(lineStationResponses), HttpStatus.OK);
    }

    // TODO implement method
    @GetMapping("/lines/{lineId}/stations/{order}")
    public ResponseEntity<LineStationResponseV1> oneStation(@PathVariable Long lineId, @PathVariable int order) {
        return null;
    }

    @GetMapping("/lines/{lineId}/fares")
    public ResponseEntity<FarePolicyListResponse> allFarePolicies(@PathVariable Long lineId) {
        List<FarePolicyResponse> fares = farePolicyService.findAllByLine(lineId).stream()
                .map(FarePolicyResponse::of)
                .toList();

        logger.info("LineId: {}, FarePolicies: {}", lineId, fares);
        return new ResponseEntity<>(new FarePolicyListResponse(fares), HttpStatus.OK);
    }

    @GetMapping("/lines/{lineId}/fares/search")
    public ResponseEntity<FareResponse> getFare(@PathVariable Long lineId, @RequestParam("distance") Double distance) {
        int fare = farePolicyService.getFare(lineId, distance);

        logger.info("LineId: {} / distance: {}", lineId, distance);
        logger.info("Fare: {}", fare);
        return new ResponseEntity<>(new FareResponse(fare), HttpStatus.OK);
    }

    private void validateLineNumberFormat(String lineNumber) {
        String onlyEns = "^[A-Z]*$";

        if (!Pattern.matches(onlyEns, lineNumber)) {
            throw new IllegalLineNumberFormatException(lineNumber);
        }

    }
}
