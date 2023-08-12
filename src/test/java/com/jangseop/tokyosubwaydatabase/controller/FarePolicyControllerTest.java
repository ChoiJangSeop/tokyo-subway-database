package com.jangseop.tokyosubwaydatabase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jangseop.tokyosubwaydatabase.controller.dto.request.FarePolicyCreateRequest;
import com.jangseop.tokyosubwaydatabase.exception.illegalformat.DistanceRangeOverlapException;
import com.jangseop.tokyosubwaydatabase.service.FarePolicyService;
import com.jangseop.tokyosubwaydatabase.util.createdto.FarePolicyCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.jangseop.tokyosubwaydatabase.exception.illegalformat.DistanceRangeOverlapException.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FarePolicyController.class)
class FarePolicyControllerTest {

    @MockBean
    FarePolicyService farePolicyService;

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;


    @Test
    @DisplayName("거리 형식이 잘못 된 경우, 예외를 응답합니다.")
    public void handleIllegalDistanceRangeException() throws Exception {
        // given
        Long testLineId = 1L;
        Double testMinDistance = 1.0;
        Double testMaxDistance = 0.0;
        int testFare = 1;

        FarePolicyCreateRequest request = FarePolicyCreateRequest.of(testLineId, testMinDistance, testMaxDistance, testFare);
        String content = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/farePolicy")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
        // then
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(is("right bound is less then left bound")))
                .andExpect(jsonPath("$.status").value(is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.errorField.minDistance").value(is(testMinDistance)))
                .andExpect(jsonPath("$.errorField.maxDistance").value(is(testMaxDistance)));
    }

    @Test
    @DisplayName("요금이 음수일 경우, 예외를 응답합니다.")
    public void handleIllegalFareException() throws Exception {
        // given
        Long testLineId = 1L;
        Double testMinDistance = 0.0;
        Double testMaxDistance = 1.0;
        int testFare = -1;

        FarePolicyCreateRequest request = FarePolicyCreateRequest.of(testLineId, testMinDistance, testMaxDistance, testFare);
        String content = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/farePolicy")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
        // then
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(is("A fare must be better or same then 0")))
                .andExpect(jsonPath("$.status").value(is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.errorField").value(is(testFare)));
    }

    @Test
    @DisplayName("입력한 거리 범위가 이미 존재하는 범위와 겹칠 경우, 예외를 응답합니다.")
    public void handleDistanceRangeOverlapException() throws Exception {
        // given
        Long testLineId = 1L;
        Double testMinDistance = 0.0;
        Double testMaxDistance = 1.0;
        int testFare = 1;

        FarePolicyCreateRequest request = FarePolicyCreateRequest.of(testLineId, testMinDistance, testMaxDistance, testFare);
        FarePolicyCreateDto dto = FarePolicyCreateDto.of(testLineId, testMinDistance, testMaxDistance, testFare);
        String content = objectMapper.writeValueAsString(request);

        when(farePolicyService.create(dto)).thenThrow(new DistanceRangeOverlapException(new FareRange(testMinDistance, testMaxDistance)));

        // when
        mockMvc.perform(post("/farePolicy")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
        // then
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(is(String.format("(%f ~ %f) range overlaps with existed fare policy.", testMinDistance, testMaxDistance))))
                .andExpect(jsonPath("$.status").value(is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.errorField.minDistance").value(is(testMinDistance)))
                .andExpect(jsonPath("$.errorField.maxDistance").value(is(testMaxDistance)));
//                .andExpect(jsonPath("$.errorField").value(is(new FareRange(testMinDistance, testMaxDistance))));
    }
}