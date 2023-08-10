package com.jangseop.tokyosubwaydatabase.controller.dto.request;

public record LineCreateRequest(Long companyId, String nameKr, String nameEn, String nameJp, String number) {}
