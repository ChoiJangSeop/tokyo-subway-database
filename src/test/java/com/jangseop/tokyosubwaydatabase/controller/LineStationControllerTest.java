package com.jangseop.tokyosubwaydatabase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jangseop.tokyosubwaydatabase.controller.dto.request.LineStationCreateRequest;
import com.jangseop.tokyosubwaydatabase.domain.Line;
import com.jangseop.tokyosubwaydatabase.domain.LineStation;
import com.jangseop.tokyosubwaydatabase.domain.LineStationIdentifier;
import com.jangseop.tokyosubwaydatabase.domain.Station;
import com.jangseop.tokyosubwaydatabase.exception.notfound.LineNotFoundException;
import com.jangseop.tokyosubwaydatabase.service.LineService;
import com.jangseop.tokyosubwaydatabase.service.LineStationService;
import com.jangseop.tokyosubwaydatabase.service.StationService;
import com.jangseop.tokyosubwaydatabase.util.createdto.LineStationCreateDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static java.util.Collections.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(LineStationController.class)
class LineStationControllerTest {

    @MockBean
    LineStationService lineStationService;

    @MockBean
    LineService lineService;

    @MockBean
    StationService stationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("잘못된 형식의 노선역 번호를 입력 시, 예외를 응답합니다.")
    public void handleIllegalLineStationNumberFormatException() throws Exception {
        // given
        String testName = "01T";
        Long testLineId = 1L;
        Long testStationId = 2L;
        double testDistance = 1.0;

        LineStationCreateRequest request = LineStationCreateRequest.of(testName, testLineId, testStationId, testDistance);
        String content = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/lineStations")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
        // then
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(is(String.format("A Line of number (%s) is not existed", testName))))
                .andExpect(jsonPath("$.status").value(is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.errorField").value(is(testName)));
    }

    @Test
    @DisplayName("노선역 번호의 노선이 존재하지 않을 경우, 예외를 응답합니다.")
    public void handleLineNotFoundException() throws Exception {
        // given
        String testName = "T01";
        String testLineNumber = "T";
        Long testLineId = 1L;
        Long testStationId = 2L;
        double testDistance = 1.0;

        LineStationCreateRequest request = LineStationCreateRequest.of(testName, testLineId, testStationId, testDistance);
        String content = objectMapper.writeValueAsString(request);

        when(lineStationService.create(LineStationCreateDto.of(testName, testLineId, testStationId, testDistance)))
                .thenThrow(new LineNotFoundException(testLineNumber));

        // when
        mockMvc.perform(post("/lineStations")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
        // then
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(is(String.format("Line number (%s) is not found.", testLineNumber))))
                .andExpect(jsonPath("$.status").value(is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.errorField").value(is(testLineNumber)));
    }

    @Test
    @DisplayName("특정 노선의 모든 노선역을 조회합니다.")
    public void testGetAllLineStationsByLine() throws Exception {
        // given
        Long testLineStationId = 1L;

        Long testLineId = 2L;
        String testLineNameKr = "lineKr";
        String testLineNameEn = "lineEn";
        String testLineNameJp = "lineJp";

        Long testStationId = 3L;
        String testStationNameKr = "stationKr";
        String testStationNameEn = "stationEn";
        String testStationNameJp = "stationJp";

        String testLineStationNumber = "T01";
        double testDistance = 0.0;
        LocalTime testDepartAt = LocalTime.NOON;

        when(lineStationService.findAllByLine(testLineId))
                .thenReturn(List.of(new LineStation(testLineStationId, testLineStationNumber, testLineId, testStationId, emptyList(), testDistance, testDepartAt)));

        when(lineService.findById(testLineId))
                .thenReturn(new Line(testLineId, 1L, testLineNameKr, testLineNameJp, testLineNameEn, "", "", emptyList(), emptyList()));

        when(stationService.findById(testStationId)).thenReturn(new Station(testStationId, emptyList(), testStationNameKr, testStationNameEn, testStationNameJp));

        // when
        mockMvc.perform(get("/lineStations/lines/{lineId}", testLineId))
                .andDo(print())
        // then
                .andExpect(jsonPath("$.lineStations.length()").value(is(1)))
                .andExpect(jsonPath("$.lineStations[0].id").value(is(testLineStationId.intValue())))
                .andExpect(jsonPath("$.lineStations[0].lineId").value(is(testLineId.intValue())))
                .andExpect(jsonPath("$.lineStations[0].lineName.kr").value(is(testLineNameKr)))
                .andExpect(jsonPath("$.lineStations[0].lineName.en").value(is(testLineNameEn)))
                .andExpect(jsonPath("$.lineStations[0].lineName.jp").value(is(testLineNameJp)))
                .andExpect(jsonPath("$.lineStations[0].stationId").value(is(testStationId.intValue())))
                .andExpect(jsonPath("$.lineStations[0].stationName.kr").value(is(testStationNameKr)))
                .andExpect(jsonPath("$.lineStations[0].stationName.en").value(is(testStationNameEn)))
                .andExpect(jsonPath("$.lineStations[0].stationName.jp").value(is(testStationNameJp)))
                .andExpect(jsonPath("$.lineStations[0].number").value(is(testLineStationNumber)))
                .andExpect(jsonPath("$.lineStations[0].distance").value(is(testDistance)));
//                .andExpect(jsonPath("$.lineStations[0].departAt").value(is(testDepartAt.)));
    }

    @Test
    @DisplayName("특정 역의 모든 노선역을 조회합니다.")
    public void testGetAllLineStationsByStation() throws Exception {
        // given
        Long testLineStationId = 1L;

        Long testLineId = 2L;
        String testLineNameKr = "lineKr";
        String testLineNameEn = "lineEn";
        String testLineNameJp = "lineJp";

        Long testStationId = 3L;
        String testStationNameKr = "stationKr";
        String testStationNameEn = "stationEn";
        String testStationNameJp = "stationJp";

        String testLineStationNumber = "T01";
        double testDistance = 0.0;
        LocalTime testDepartAt = LocalTime.NOON;

        when(lineStationService.findAllByStation(testStationId))
                .thenReturn(List.of(new LineStation(testLineStationId, testLineStationNumber, testLineId, testStationId, emptyList(), testDistance, testDepartAt)));

        when(lineService.findById(testLineId))
                .thenReturn(new Line(testLineId, 1L, testLineNameKr, testLineNameJp, testLineNameEn, "", "", emptyList(), emptyList()));

        when(stationService.findById(testStationId)).thenReturn(new Station(testStationId, emptyList(), testStationNameKr, testStationNameEn, testStationNameJp));

        // when
        mockMvc.perform(get("/lineStations/stations/{stationId}", testStationId))
                .andDo(print())
        // then
                .andExpect(jsonPath("$.lineStations.length()").value(is(1)))
                .andExpect(jsonPath("$.lineStations[0].id").value(is(testLineStationId.intValue())))
                .andExpect(jsonPath("$.lineStations[0].lineId").value(is(testLineId.intValue())))
                .andExpect(jsonPath("$.lineStations[0].lineName.kr").value(is(testLineNameKr)))
                .andExpect(jsonPath("$.lineStations[0].lineName.en").value(is(testLineNameEn)))
                .andExpect(jsonPath("$.lineStations[0].lineName.jp").value(is(testLineNameJp)))
                .andExpect(jsonPath("$.lineStations[0].stationId").value(is(testStationId.intValue())))
                .andExpect(jsonPath("$.lineStations[0].stationName.kr").value(is(testStationNameKr)))
                .andExpect(jsonPath("$.lineStations[0].stationName.en").value(is(testStationNameEn)))
                .andExpect(jsonPath("$.lineStations[0].stationName.jp").value(is(testStationNameJp)))
                .andExpect(jsonPath("$.lineStations[0].number").value(is(testLineStationNumber)))
                .andExpect(jsonPath("$.lineStations[0].distance").value(is(testDistance)));
//                .andExpect(jsonPath("$.lineStations[0].departAt").value(is(testDepartAt)));
    }

    @Test
    @DisplayName("특정 노선역을 조회합니다")
    public void testGetLineStation() throws Exception {
        // given
        Long testLineStationId = 1L;

        Long testLineId = 2L;
        String testLineNameKr = "lineKr";
        String testLineNameEn = "lineEn";
        String testLineNameJp = "lineJp";

        Long testStationId = 3L;
        String testStationNameKr = "stationKr";
        String testStationNameEn = "stationEn";
        String testStationNameJp = "stationJp";

        String testLineStationNumber = "T01";
        double testDistance = 0.0;
        LocalTime testDepartAt = LocalTime.NOON;

        when(lineStationService.findByIdentifier(LineStationIdentifier.of(testLineId, testStationId)))
                .thenReturn(new LineStation(testLineStationId, testLineStationNumber, testLineId, testStationId, emptyList(), testDistance, testDepartAt));

        when(lineService.findById(testLineId))
                .thenReturn(new Line(testLineId, 1L, testLineNameKr, testLineNameJp, testLineNameEn, "", "", emptyList(), emptyList()));

        when(stationService.findById(testStationId)).thenReturn(new Station(testStationId, emptyList(), testStationNameKr, testStationNameEn, testStationNameJp));


        // when
        mockMvc.perform(get("/lineStations/lines/{lineId}/stations/{stationId}", testLineId, testStationId))
                .andDo(print())
        // then
                .andExpect(jsonPath("$.id").value(is(testLineStationId.intValue())))
                .andExpect(jsonPath("$.lineId").value(is(testLineId.intValue())))
                .andExpect(jsonPath("$.lineName.kr").value(is(testLineNameKr)))
                .andExpect(jsonPath("$.lineName.en").value(is(testLineNameEn)))
                .andExpect(jsonPath("$.lineName.jp").value(is(testLineNameJp)))
                .andExpect(jsonPath("$.stationId").value(is(testStationId.intValue())))
                .andExpect(jsonPath("$.stationName.kr").value(is(testStationNameKr)))
                .andExpect(jsonPath("$.stationName.en").value(is(testStationNameEn)))
                .andExpect(jsonPath("$.stationName.jp").value(is(testStationNameJp)))
                .andExpect(jsonPath("$.number").value(is(testLineStationNumber)))
                .andExpect(jsonPath("$.distance").value(is(testDistance)));
//                .andExpect(jsonPath("$.departAt").value(is(testDepartAt)));
                // QUESTION
                //  시간 비교 어떻게??
    }

    @Test
    @Disabled
    @DisplayName("노선역 번호로 특정 노선역을 조회합니다")
    public void testGetLineStationByNumber() throws Exception {
        // given
        Long testLineStationId = 1L;

        Long testLineId = 2L;
        String testLineNameKr = "lineKr";
        String testLineNameEn = "lineEn";
        String testLineNameJp = "lineJp";

        Long testStationId = 3L;
        String testStationNameKr = "stationKr";
        String testStationNameEn = "stationEn";
        String testStationNameJp = "stationJp";

        String testLineStationNumber = "T01";
        double testDistance = 0.0;
        LocalTime testDepartAt = LocalTime.NOON;

//        when(lineStationService.findByIdentifier(LineStationIdentifier.of(testLineId, testStationId)))
//                .thenReturn(new LineStation(testLineStationId, testLineStationNumber, testLineId, testStationId, emptyList(), testDistance, testDepartAt));

        when(lineService.findById(testLineId))
                .thenReturn(new Line(testLineId, 1L, testLineNameKr, testLineNameJp, testLineNameEn, "", "", emptyList(), emptyList()));

        when(stationService.findById(testStationId)).thenReturn(new Station(testStationId, emptyList(), testStationNameKr, testStationNameEn, testStationNameJp));


        // when
        mockMvc.perform(get("/lineStations/lines/{lineId}/stations/{stationId}", testLineId, testStationId))
                .andDo(print())
                // then
                .andExpect(jsonPath("$.id").value(is(testLineStationId.intValue())))
                .andExpect(jsonPath("$.lineId").value(is(testLineId.intValue())))
                .andExpect(jsonPath("$.lineName.kr").value(is(testLineNameKr)))
                .andExpect(jsonPath("$.lineName.en").value(is(testLineNameEn)))
                .andExpect(jsonPath("$.lineName.jp").value(is(testLineNameJp)))
                .andExpect(jsonPath("$.stationId").value(is(testStationId.intValue())))
                .andExpect(jsonPath("$.stationName.kr").value(is(testStationNameKr)))
                .andExpect(jsonPath("$.stationName.en").value(is(testStationNameEn)))
                .andExpect(jsonPath("$.stationName.jp").value(is(testStationNameJp)))
                .andExpect(jsonPath("$.number").value(is(testLineStationNumber)))
                .andExpect(jsonPath("$.distance").value(is(testDistance)));
//                .andExpect(jsonPath("$.departAt").value(is(testDepartAt)));
    }

}