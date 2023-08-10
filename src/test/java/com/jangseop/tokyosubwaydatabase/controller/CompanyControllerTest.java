package com.jangseop.tokyosubwaydatabase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jangseop.tokyosubwaydatabase.controller.dto.request.CompanyCreateRequest;
import com.jangseop.tokyosubwaydatabase.exception.duplicated.CompanyNameDuplicatedException;
import com.jangseop.tokyosubwaydatabase.service.CompanyService;
import com.jangseop.tokyosubwaydatabase.util.create_dto.CompanyCreateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    @MockBean
    private CompanyService companyService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회사를 생성합니다")
    public void testPostCompany() throws Exception {
        // given
        String testName = "test";
        CompanyCreateRequest request = new CompanyCreateRequest(testName);
        String content = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/company")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
        // then
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회사명이 중복될시, 예외를 응답합니다.")
    public void handleCompanyNameDuplication() throws Exception {
        // given
        String testName = "test";
        CompanyCreateRequest request = new CompanyCreateRequest(testName);
        String content = objectMapper.writeValueAsString(request);

        when(companyService.create(CompanyCreateDto.of(testName)))
                .thenThrow(new CompanyNameDuplicatedException(testName));

        // when
        mockMvc.perform(post("/company")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
        // then
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value(is(String.format("Company name (%s) is already existed.", testName))))
                .andExpect(jsonPath("$.status").value(is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(jsonPath("$.errorField").value(is(testName)));
    }


}