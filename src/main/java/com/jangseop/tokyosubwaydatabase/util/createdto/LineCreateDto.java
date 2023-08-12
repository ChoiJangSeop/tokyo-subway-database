package com.jangseop.tokyosubwaydatabase.util.createdto;

public record LineCreateDto(Long companyId, String nameKr, String nameEn, String nameJp, String number) {

    public static LineCreateDto of(Long companyId, String nameKr, String nameEn, String nameJp, String number) {
        return new LineCreateDto(companyId, nameKr, nameEn, nameJp, number);
    }
}
