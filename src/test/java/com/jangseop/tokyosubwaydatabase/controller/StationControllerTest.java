package com.jangseop.tokyosubwaydatabase.controller;

import com.jangseop.tokyosubwaydatabase.domain.Company;
import com.jangseop.tokyosubwaydatabase.domain.Line;
import com.jangseop.tokyosubwaydatabase.domain.LineStation;
import com.jangseop.tokyosubwaydatabase.domain.Station;
import com.jangseop.tokyosubwaydatabase.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static java.util.Collections.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

    @MockBean
    private FarePolicyService farePolicyService;

    @Autowired
    private MockMvc mockMvc;

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

        when(lineStationService.findAllByStation(testStationId)).thenReturn(List.of(
                new LineStation(testLineStationId, testLineStationNumber, testLineId, testStationId, emptyList(), testLineStationDistance, testLineDepartAt)));

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
}