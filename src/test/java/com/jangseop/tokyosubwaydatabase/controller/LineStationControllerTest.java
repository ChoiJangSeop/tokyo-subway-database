package com.jangseop.tokyosubwaydatabase.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(LineStationController.class)
class LineStationControllerTest {

    @Test
    @DisplayName("잘못된 형식의 노선역 번호를 입력 시, 예외를 응답합니다.")
    public void handleIllegalLineStationNumberFormatException() throws Exception {
        // given

        // when

        // then
    }

}