package com.jangseop.tokyosubwaydatabase.controller.dto;

import com.jangseop.tokyosubwaydatabase.domain.Line;

import java.util.Arrays;
import java.util.List;

public record LineListResponse(List<LineResponse> lines) {

    public static LineListResponse of(List<LineResponse> lineResponses) {
        return new LineListResponse(lineResponses);
    }

}
