package com.jangseop.tokyosubwaydatabase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jangseop.tokyosubwaydatabase.controller.dto.request.FarePolicyCreateRequest;
import com.jangseop.tokyosubwaydatabase.exception.illegal_format.DistanceRangeOverlapException;
import com.jangseop.tokyosubwaydatabase.service.FarePolicyService;
import com.jangseop.tokyosubwaydatabase.util.create_dto.FarePolicyCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.jangseop.tokyosubwaydatabase.exception.illegal_format.DistanceRangeOverlapException.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
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
//                .andExpect(jsonPath("$.errorField").value(is(new FareRange(testMinDistance, testMaxDistance))));

        // QUESTION
        //    WebMvcTest 코드 작성과정에서
        //    응답(response)으로 받은 JSON 형식의 객체와 API상의 객체를 비교하고 싶은데,
        //    API상의 객체는 특정 클래스(이 케이스에서는 FareRange)로 감싸져 있어 테스트가 실패합니다.
        //    이경우, 필드별로 분리해서 비교하는게 좋은지, 아니면 다른 동일성 테스트를 할 수 있는 다른 메서드가 있는지 궁급합니다.
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