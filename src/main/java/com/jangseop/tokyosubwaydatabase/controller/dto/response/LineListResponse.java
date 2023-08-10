package com.jangseop.tokyosubwaydatabase.controller.dto.response;

import java.util.List;

public record LineListResponse(List<LineResponse> lines) {

    public static LineListResponse of(List<LineResponse> lineResponses) {
        return new LineListResponse(lineResponses);
    }

}
