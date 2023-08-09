package com.jangseop.tokyosubwaydatabase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jangseop.tokyosubwaydatabase.domain.*;
import com.jangseop.tokyosubwaydatabase.exception.duplicated.LineNumberDuplicationException;
import com.jangseop.tokyosubwaydatabase.exception.illegal_format.IllegalLineNumberFormatException;
import com.jangseop.tokyosubwaydatabase.exception.not_found.CompanyNotFoundException;
import com.jangseop.tokyosubwaydatabase.exception.not_found.LineNotFoundException;
import com.jangseop.tokyosubwaydatabase.repository.LineRepository;
import com.jangseop.tokyosubwaydatabase.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalTime;
import java.util.List;

import static com.jangseop.tokyosubwaydatabase.service.LineService.*;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LineController.class)
class LineControllerTest {

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

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    @DisplayName("모든 노선의 정보를 조회한다")
    public void testGetAllLines() throws Exception {
        // given

        Long testLineId = 1L;
        String testLineNumber = "T";
        String testLineNameKr = "nameKr";
        String testLineNameEn = "nameEn";
        String testLineNameJp = "nameJp";

        Long testCompanyId = 2L;
        String testCompanyName = "testCompany";

        when(lineService.findAll()).thenReturn(List.of(
                new Line(testLineId, testCompanyId, testLineNameKr, testLineNameJp, testLineNameEn, testLineNumber, "", emptyList(), emptyList())
        ));

        when(companyService.findById(testCompanyId)).thenReturn(new Company(testCompanyId, testCompanyName, emptyList()));

        // when
        mockMvc.perform(get("/lines"))
                .andDo(print())
        // then
                .andExpect(jsonPath("$.lines.length()").value(is(1)))
                .andExpect(jsonPath("$.lines[0].id").value(is(testLineId.intValue())))
                .andExpect(jsonPath("$.lines[0].number").value(is(testLineNumber)))
                .andExpect(jsonPath("$.lines[0].companyName").value(is(testCompanyName)))
                .andExpect(jsonPath("$.lines[0].nameKr").value(is(testLineNameKr)))
                .andExpect(jsonPath("$.lines[0].nameJp").value(is(testLineNameJp)))
                .andExpect(jsonPath("$.lines[0].nameEn").value(is(testLineNameEn)));
    }

    @Test
    @DisplayName("노선의 정보를 조회한다")
    public void testGetLine() throws Exception {
        // given
        Long id = 1L;
        String number = "T";
        Long companyId = 2L;
        String companyName = "test_company";
        String nameKr = "test_kr";
        String nameEn = "test_en";
        String nameJp  = "test_jp";

        when(lineService.findById(id)).thenReturn(
                new Line(id, companyId, nameKr, nameJp, nameEn, number, "", emptyList(), emptyList()));

        when(companyService.findById(companyId)).thenReturn(
                new Company(companyId, companyName, emptyList()));

        // when
        mockMvc.perform(get("/lines/{lineId}", id))
                .andDo(print())
        // then
                .andExpect(jsonPath("$.id").value(is(id.intValue())))
                .andExpect(jsonPath("$.companyName").value(is(companyName)))
                .andExpect(jsonPath("$.nameKr").value(is(nameKr)))
                .andExpect(jsonPath("$.nameEn").value(is(nameEn)))
                .andExpect(jsonPath("$.nameJp").value(is(nameJp)));
    }

    @Test
    @DisplayName("노선에 포함된 모든 역 정보를 조회합니다")
    public void testGetAllStationsInLine() throws Exception {
        // given

        Long testLineId = 1L;

        Long testStationId1 = 2L;
        String testStationNameKr1 = "station_name_kr1";
        String testStationNameEn1 = "station_name_en1";
        String testStationNameJp1 = "station_name_jp1";

        Long testStationId2 = 3L;
        String testStationNameKr2 = "station_name_kr2";
        String testStationNameEn2 = "station_name_en2";
        String testStationNameJp2 = "station_name_jp2";

        Long testLineStationId1 = 4L;
        String testLineStationNumber1 = "T01";
        double testDistance1 = 0.1;
        LocalTime testDepartAt1 = LocalTime.NOON;

        Long testLineStationId2 = 5L;
        String testLineStationNumber2 = "T02";
        double testDistance2 = 0.2;
        LocalTime testDepartAt2 = LocalTime.NOON;

        when(lineStationService.findAllByLine(testLineId)).thenReturn(List.of(
                new LineStation(testLineStationId1, testLineStationNumber1, testLineId, testStationId1, emptyList(), testDistance1, testDepartAt1),
                new LineStation(testLineStationId2, testLineStationNumber2, testLineId, testStationId2, emptyList(), testDistance2, testDepartAt2)
        ));

        when(stationService.findById(testStationId1)).thenReturn(
                new Station(testStationId1, emptyList(), testStationNameKr1, testStationNameEn1, testStationNameJp1));

        when(stationService.findById(testStationId2)).thenReturn(
                new Station(testStationId2, emptyList(), testStationNameKr2, testStationNameEn2, testStationNameJp2));

        // when
        mockMvc.perform(get("/lines/{lineId}/stations", testLineId))
                .andDo(print())
        // then
                .andExpect(jsonPath("$.stations.length()").value(is(2)))
                .andExpect(jsonPath("$.stations[0].id").value(is(testLineStationId1.intValue())))
                .andExpect(jsonPath("$.stations[0].number").value(is(testLineStationNumber1)))
                .andExpect(jsonPath("$.stations[0].nameKr").value(is(testStationNameKr1)))
                .andExpect(jsonPath("$.stations[0].nameEn").value(is(testStationNameEn1)))
                .andExpect(jsonPath("$.stations[0].nameJp").value(is(testStationNameJp1)))
                .andExpect(jsonPath("$.stations[0].distance").value(is(testDistance1)));
    }
    
    @Test
    @DisplayName("노선에 포함된 역 정보를 조회합니다")
    public void testGetStationInLine() throws Exception {

        // TODO
        //  * 노선역 번호 기반 조회 메서드
        //  * 역 순서 관련 칼럼 추가

        // given
        Long testLineId = 1L;

        Long testStationId1 = 2L;
        String testStationNameKr1 = "station_name_kr1";
        String testStationNameEn1 = "station_name_en1";
        String testStationNameJp1 = "station_name_jp1";

        Long testLineStationId1 = 3L;
        String testLineStationNumber1 = "T01";
        double testDistance1 = 0.1;
        LocalTime testDepartAt1 = LocalTime.NOON;

        // when
        
        // then
        fail();
    }

    @Test
    @DisplayName("노선의 운임 정책을 조회합니다")
    public void testGetFarePolicy() throws Exception {
        // given
        Long testLineId = 1L;

        Long testFarePolicyId = 2L;
        Double testMinDistance = 1.0;
        Double testMaxDistance = 10.0;
        int testFare = 1;


        when(farePolicyService.findAllByLine(testLineId)).thenReturn(List.of(
                new FarePolicy(testFarePolicyId, testLineId, testMinDistance, testMaxDistance, testFare)
        ));

        // when
        mockMvc.perform(get("/lines/{lineId}/fares", testLineId))
                .andDo(print())
        // then
                .andExpect(jsonPath("$.fares.length()").value(is(1)))
                .andExpect(jsonPath("$.fares[0].id").value(is(testFarePolicyId.intValue())))
                .andExpect(jsonPath("$.fares[0].minDistance").value(is(testMinDistance)))
                .andExpect(jsonPath("$.fares[0].maxDistance").value(is(testMaxDistance)))
                .andExpect(jsonPath("$.fares[0].fare").value(is(testFare)));
    }

    @Test
    @DisplayName("노선의 특정 거리 운임 요금을 조회합니다")
    public void testGetFarePolicyByDistance() throws Exception {
        // given
        Long testLineId = 1L;
        int testFare = 1;
        Double testDistance = 5.0;

        when(farePolicyService.getFare(testLineId, testDistance)).thenReturn(testFare);

        // when
        mockMvc.perform(get("/lines/{lineId}/fares/search?distance=" + testDistance, testLineId))
                .andDo(print())
        // then
                .andExpect(jsonPath("$.fare").value(is(testFare)));
    }

    @Test
    @DisplayName("존재하지 않는 노선을 조회할 경우, 예외를 응답합니다.")
    public void testGetLineException() throws Exception {
        // given
        Long id = 1L;
        String number = "T";
        Long companyId = 2L;
        String companyName = "test_company";
        String nameKr = "test_kr";
        String nameEn = "test_en";
        String nameJp  = "test_jp";

        when(lineService.findById(id)).thenThrow(new LineNotFoundException(id));

        // QUESTION http status 검증 어떻게???

        // when
        mockMvc.perform(get("/lines/{lineId}", id))
                .andDo(print())
                // then
                .andExpect(jsonPath("$.status").value(is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.message").value(is(String.format("Line id (%s) is not found.", id))))
                .andExpect(jsonPath("$.errorField").value(is(id.intValue())));
    }

    // QUESTION
    //  노선번호 중복에 의한 예외는 이미 서비스 단위에서 테스트가 끝남.
    //  그럼 컨트롤러 수준에서는 단순히 예외가 던져진다고 가정하고, 예외에 대한 응답(response) 형식만 테스트 하면 되나?

    @Test
    @DisplayName("중복된 노선 번호로 노선 생성 요청시, 예외를 응답합니다.")
    public void testHandleLineNumberDuplicationException() throws Exception {
        // given
        Long testCompanyId = 1L;
        String testNameKr = "nameKr";
        String testNameEn = "nameEn";
        String testNameJp = "nameJp";
        String testNumber = "T";

        LineCreateDto testCreateDto = LineCreateDto.of(testCompanyId, testNameKr, testNameEn, testNameJp, testNumber);

        when(lineService.create(testCreateDto))
                .thenThrow(new LineNumberDuplicationException(testNumber));

        // when
        String content = objectMapper.writeValueAsString(testCreateDto);
        mockMvc.perform(post("/lines")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
        // then
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(is(String.format("Line number (%s) is already existed", testNumber))))
                .andExpect(jsonPath("$.status").value(is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.errorField").value(is(testNumber)));
    }

    @Test
    @DisplayName("존재하지 않는 회사의 아이디로 노선 생성 요청시, 예외를 응답합니다")
    public void testHandleCompanyNotFoundException() throws Exception {
        // given
        Long testCompanyId = 1L;
        String testNameKr = "nameKr";
        String testNameEn = "nameEn";
        String testNameJp = "nameJp";
        String testNumber = "T";

        LineCreateDto testCreateDto = LineCreateDto.of(testCompanyId, testNameKr, testNameEn, testNameJp, testNumber);

        when(lineService.create(testCreateDto))
                .thenThrow(new CompanyNotFoundException(testCompanyId));

        // when
        String content = objectMapper.writeValueAsString(testCreateDto);
        mockMvc.perform(post("/lines")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
        // then
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(is(String.format("Company (%s) is not found.", testCompanyId))))
                .andExpect(jsonPath("$.status").value(is(HttpStatus.NOT_FOUND.value())))
                .andExpect(jsonPath("$.errorField").value(is(testCompanyId.intValue())));
    }

    @Test
    @DisplayName("잘못된 형식의 노선 번호로 노선 생성 요청시, 예외를 응답합니다")
    public void testHandleLineNumberFormatException() throws Exception {
        // given
        Long testCompanyId = 1L;
        String testNameKr = "nameKr";
        String testNameEn = "nameEn";
        String testNameJp = "nameJp";
        String testNumber = "T1";

        LineCreateDto testCreateDto = LineCreateDto.of(testCompanyId, testNameKr, testNameEn, testNameJp, testNumber);

        // when
        String content = objectMapper.writeValueAsString(testCreateDto);
        mockMvc.perform(post("/lines")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                // then
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(is("Line's number must consist of english only.")))
                .andExpect(jsonPath("$.status").value(is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.errorField").value(is(testNumber)));
    }
}