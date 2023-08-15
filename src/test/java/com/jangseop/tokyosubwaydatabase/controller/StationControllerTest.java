package com.jangseop.tokyosubwaydatabase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jangseop.tokyosubwaydatabase.controller.dto.request.StationCreateRequest;
import com.jangseop.tokyosubwaydatabase.domain.*;
import com.jangseop.tokyosubwaydatabase.exception.notfound.LineStationNotFoundException;
import com.jangseop.tokyosubwaydatabase.exception.notfound.StationNotFoundException;
import com.jangseop.tokyosubwaydatabase.service.*;
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

import static org.hamcrest.Matchers.is;
import static java.util.Collections.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StationController.class)
class StationControllerTest {

    @MockBean
    private LineService lineService;

    @MockBean
    private CompanyService companyService;

    @MockBean
    private LineStationService lineStationService;

    @MockBean
    private StationService stationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("모든 역을 조회합니다")
    public void testGetAllStations() throws Exception {
        // given
        Long testStationId = 1L;
        String testStationNameKr = "nameKr";
        String testStationNameEn = "nameEn";
        String testStationNameJp = "nameJp";

        when(stationService.findAll()).thenReturn(List.of(
                new Station(testStationId, emptyList(), testStationNameKr, testStationNameEn, testStationNameJp)));

        // when
        mockMvc.perform(get("/stations"))
                .andDo(print())
        // then
                .andExpect(jsonPath("$.stations.length()").value(is(1)))
                .andExpect(jsonPath("$.stations[0].id").value(is(testStationId.intValue())))
                .andExpect(jsonPath("$.stations[0].nameKr").value(is(testStationNameKr)))
                .andExpect(jsonPath("$.stations[0].nameEn").value(is(testStationNameEn)))
                .andExpect(jsonPath("$.stations[0].nameJp").value(is(testStationNameJp)));
    }

    // TODO move to integration test
    @Test
    @Disabled
    @DisplayName("역을 생성합니다")
    public void testPostStation() throws Exception {
        // given
        String testStationNameKr = "nameKr";
        String testStationNameEn = "nameEn";
        String testStationNameJp = "nameJp";

        StationCreateRequest request = new StationCreateRequest(testStationNameKr, testStationNameEn, testStationNameJp);
        String content = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/stations")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
        // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nameKr").value(is(testStationNameKr)))
                .andExpect(jsonPath("$.nameEn").value(is(testStationNameEn)))
                .andExpect(jsonPath("$.nameJp").value(is(testStationNameJp)));
    }

    @Test
    @DisplayName("역을 조회합니다 (아이디)")
    public void testGetStationById() throws Exception {
        // given
        Long testStationId = 1L;
        String testStationNameKr = "nameKr";
        String testStationNameEn = "nameEn";
        String testStationNameJp = "nameJp";

        when(stationService.findById(testStationId)).thenReturn(new Station(testStationId, emptyList(), testStationNameKr, testStationNameEn, testStationNameJp));

        // when
        mockMvc.perform(get("/stations/{stationId}", testStationId))
                .andDo(print())
        // then
                .andExpect(jsonPath("$.id").value(is(testStationId.intValue())))
                .andExpect(jsonPath("$.nameKr").value(is(testStationNameKr)))
                .andExpect(jsonPath("$.nameEn").value(is(testStationNameEn)))
                .andExpect(jsonPath("$.nameJp").value(is(testStationNameJp)));
    }

    @Test
    @DisplayName("역을 조회합니다 (역이름)")
    public void getAllStationByNameJp() throws Exception {
        // given
        Long testStationId = 1L;
        String testStationNameKr = "nameKr";
        String testStationNameEn = "nameEn";
        String testStationNameJp = "nameJp";

        when(stationService.findByNameJp(testStationNameJp)).thenReturn(
                List.of(new Station(testStationId, emptyList(), testStationNameKr, testStationNameEn, testStationNameJp)));


        // when
        mockMvc.perform(get("/stations/name/{nameJp}", testStationNameJp))
                .andDo(print())
        // then
                .andExpect(jsonPath("$.stations.length()").value(is(1)))
                .andExpect(jsonPath("$.stations[0].id").value(is(testStationId.intValue())))
                .andExpect(jsonPath("$.stations[0].nameKr").value(is(testStationNameKr)))
                .andExpect(jsonPath("$.stations[0].nameEn").value(is(testStationNameEn)))
                .andExpect(jsonPath("$.stations[0].nameJp").value(is(testStationNameJp)));
    }

    @Test
    @Disabled
    @DisplayName("역의 모든 노선을 조회합니다.")
    public void testGetAllLineByStation() throws Exception {
        // given
        Long testStationId = 1L;
        Long testLineStationId = 2L;
        Long testLineId = 3L;
        Long testCompanyId = 4L;

        String testLineStationNumber = "T01";
        double testLineStationDistance = 0.0;
        LocalTime testLineDepartAt = LocalTime.NOON;

        String testLineNameKr = "nameKr";
        String testLineNameEn = "nameEn";
        String testLineNameJp = "nameJp";
        String testLineNumber = "T";

        String testCompanyName = "testCompany";

        when(lineStationService.findAllByStation(testStationId)).thenReturn(List.of(
                new LineStation(testLineStationId, testLineStationNumber, testLineId, testStationId, emptyList(), testLineStationDistance, testLineDepartAt)));

        when(lineService.findById(testLineId)).thenReturn(
                new Line(testLineId, testCompanyId, testLineNameKr, testLineNameJp, testLineNameEn, testLineNumber, "", emptyList(), emptyList()));

        when(companyService.findById(testCompanyId)).thenReturn(new Company(testCompanyId, testCompanyName, emptyList()));

        // when
        mockMvc.perform(get("/stations/{stationId}/lines", testStationId))
                .andDo(print())
        // then
                .andExpect(jsonPath("$.lines.length()").value(is(1)))
                .andExpect(jsonPath("$.lines[0].id").value(is(testLineStationId.intValue())))
                .andExpect(jsonPath("$.lines[0].number").value(is(testLineStationNumber)))
                .andExpect(jsonPath("$.lines[0].company").value(is(testCompanyName)))
                .andExpect(jsonPath("$.lines[0].nameKr").value(is(testLineNameKr)))
                .andExpect(jsonPath("$.lines[0].nameEn").value(is(testLineNameEn)))
                .andExpect(jsonPath("$.lines[0].nameJp").value(is(testLineNameJp)))
                .andExpect(jsonPath("$.lines[0].distance").value(is(testLineStationDistance)));
    }
    
    @Test
    @Disabled
    @DisplayName("특정 노선 역을 조회합니다")
    public void testGetOneLine() throws Exception {
        // given
        Long testStationId = 1L;
        Long testLineStationId = 2L;
        Long testLineId = 3L;
        Long testCompanyId = 4L;

        String testLineStationNumber = "T01";
        double testLineStationDistance = 0.0;
        LocalTime testLineDepartAt = LocalTime.NOON;

        String testLineNameKr = "nameKr";
        String testLineNameEn = "nameEn";
        String testLineNameJp = "nameJp";
        String testLineNumber = "T";

        String testCompanyName = "testCompany";

//        when(lineStationService.findAllByStation(testStationId)).thenReturn(List.of(
//                new LineStation(testLineStationId, testLineStationNumber, testLineId, testStationId, emptyList(), testLineStationDistance, testLineDepartAt)));

        when(lineStationService.findByIdentifier(LineStationIdentifier.of(testLineId, testStationId))).thenReturn(
                new LineStation(testLineStationId, testLineStationNumber, testLineId, testStationId, emptyList(), testLineStationDistance, testLineDepartAt));

        when(lineService.findById(testLineId)).thenReturn(
                new Line(testLineId, testCompanyId, testLineNameKr, testLineNameJp, testLineNameEn, testLineNumber, "", emptyList(), emptyList()));

        when(companyService.findById(testCompanyId)).thenReturn(new Company(testCompanyId, testCompanyName, emptyList()));

        // when
        mockMvc.perform(get("/stations/{stationId}/lines/{lineId}", testStationId, testLineId))
                .andDo(print())
                // then
                .andExpect(jsonPath("$.id").value(is(testLineStationId.intValue())))
                .andExpect(jsonPath("$.number").value(is(testLineStationNumber)))
                .andExpect(jsonPath("$.company").value(is(testCompanyName)))
                .andExpect(jsonPath("$.nameKr").value(is(testLineNameKr)))
                .andExpect(jsonPath("$.nameEn").value(is(testLineNameEn)))
                .andExpect(jsonPath("$.nameJp").value(is(testLineNameJp)))
                .andExpect(jsonPath("$.distance").value(is(testLineStationDistance)));
    }

    @Test
    @DisplayName("존재하지 않는 역을 조회할 경우, 예외를 응답합니다 (아이디)")
    public void testStationNotFoundException() throws Exception {
        // given
        Long testId = 1L;
        when(stationService.findById(testId)).thenThrow(new StationNotFoundException(testId));

        // when
        mockMvc.perform(get("/stations/{stationId}", testId))
                .andDo(print())
        // then
                .andExpect(handler().handlerType(StationController.class))
                .andExpect(jsonPath("$.message").value(is(String.format("A Station of id (%s) is not found.", testId))))
                .andExpect(jsonPath("$.status").value(is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.errorField").value(is(testId.intValue())));
    }

    @Test
    @Disabled
    @DisplayName("존재하지 않는 노선역을 조회할 경우, 예외를 응답합니다 (역 아이디, 노선 아이디)")
    public void testLineStationNotFoundException() throws Exception {
        // given
        Long testStationId = 1L;
        Long testLineId = 2L;
        LineStationIdentifier testIdentifier = LineStationIdentifier.of(testStationId, testLineId);

        when(lineStationService.findByIdentifier(testIdentifier)).thenThrow(new LineStationNotFoundException(testIdentifier));

        // when
        mockMvc.perform(get("/lines/{lineId}/stations/{stationId}", testLineId, testStationId))
                .andDo(print())
                // then
                .andExpect(handler().handlerType(LineStationController.class))
                .andExpect(jsonPath("$.message")
                        .value(is(String.format("A LineStation of lineId (%s) and stationId (%s) is not  found.", testIdentifier.lineId(), testIdentifier.stationId()))))
                .andExpect(jsonPath("$.status").value(is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.errorField").value(is(testIdentifier)));
    }
}