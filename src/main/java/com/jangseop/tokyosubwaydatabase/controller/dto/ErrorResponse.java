package com.jangseop.tokyosubwaydatabase.controller.dto;

public record ErrorResponse(String message, int status, Object errorField) {}

