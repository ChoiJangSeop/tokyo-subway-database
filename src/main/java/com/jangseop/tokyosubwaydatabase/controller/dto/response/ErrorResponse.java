package com.jangseop.tokyosubwaydatabase.controller.dto.response;

public record ErrorResponse(String message, int status, Object errorField) {}

