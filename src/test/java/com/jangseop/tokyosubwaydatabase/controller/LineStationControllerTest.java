package com.jangseop.tokyosubwaydatabase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jangseop.tokyosubwaydatabase.controller.dto.request.LineStationCreateRequest;
import com.jangseop.tokyosubwaydatabase.exception.not_found.LineNotFoundException;
import com.jangseop.tokyosubwaydatabase.repository.LineRepository;
import com.jangseop.tokyosubwaydatabase.service.LineStationService;
import com.jangseop.tokyosubwaydatabase.util.create_dto.LineStationCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(LineStationController.class)
class LineStationControllerTest {

    @MockBean
    LineStationService lineStationService;

    @MockBean
    LineRepository lineRepository;

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

}