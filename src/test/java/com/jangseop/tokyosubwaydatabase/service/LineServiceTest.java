package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Line;
import com.jangseop.tokyosubwaydatabase.entity.CompanyEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import com.jangseop.tokyosubwaydatabase.repository.CompanyRepository;
import com.jangseop.tokyosubwaydatabase.repository.LineRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LineServiceTest {

    @DisplayName("노선을 생성한다")
    @Test
    public void create() throws Exception {
        // given

        // when

        // then
        fail();
    }

    @DisplayName("이름 중복 시, 예외를 던진다")
    @Test
    public void nameDuplication() throws Exception {
        // given

        // when

        // then
        fail();
    }

    @DisplayName("노선을 조회한다(아이디)")
    @Test
    public void findById() throws Exception {
        // given
        LineEntity lineEntity = mock(LineEntity.class);
        CompanyEntity companyEntity = mock(CompanyEntity.class);

        Long testLineId = 1L;
        String testLineNameKr = "긴자선";
        String testLineNameEn = "Ginza Line";
        String testLineNameJp = "銀座線";
        String testLineNumber = "G";

        when(lineEntity.getId()).thenReturn(testLineId);
        when(lineEntity.getNameKr()).thenReturn(testLineNameKr);
        when(lineEntity.getNameEn()).thenReturn(testLineNameEn);
        when(lineEntity.getNameJp()).thenReturn(testLineNameJp);
        when(lineEntity.getNumber()).thenReturn(testLineNumber);
        when(lineEntity.getCompany()).thenReturn(companyEntity);

        LineRepository lineRepository = mock(LineRepository.class);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(lineRepository.findById(testLineId)).thenReturn(Optional.of(lineEntity));

        // when
        LineService lineService = new LineServiceImpl(lineRepository, companyRepository);
        Line findLine = lineService.findById(testLineId);

        // then
        assertThat(findLine.id()).isEqualTo(testLineId);
        assertThat(findLine.nameKr()).isEqualTo(testLineNameKr);
        assertThat(findLine.nameEn()).isEqualTo(testLineNameEn);
        assertThat(findLine.nameJp()).isEqualTo(testLineNameJp);
        assertThat(findLine.number()).isEqualTo(testLineNumber);
    }

    @DisplayName("노선을 조회한다(노선번호)")
    @Test
    public void findByNumber() throws Exception {
        // given
        LineEntity lineEntity = mock(LineEntity.class);
        CompanyEntity companyEntity = mock(CompanyEntity.class);

        Long testLineId = 1L;
        String testLineNameKr = "긴자선";
        String testLineNameEn = "Ginza Line";
        String testLineNameJp = "銀座線";
        String testLineNumber = "G";

        when(lineEntity.getId()).thenReturn(testLineId);
        when(lineEntity.getNameKr()).thenReturn(testLineNameKr);
        when(lineEntity.getNameEn()).thenReturn(testLineNameEn);
        when(lineEntity.getNameJp()).thenReturn(testLineNameJp);
        when(lineEntity.getNumber()).thenReturn(testLineNumber);
        when(lineEntity.getCompany()).thenReturn(companyEntity);

        LineRepository lineRepository = mock(LineRepository.class);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(lineRepository.findByNumber(testLineNumber)).thenReturn(Optional.of(lineEntity));

        // when
        LineService lineService = new LineServiceImpl(lineRepository, companyRepository);
        Line findLine = lineService.findByNumber(testLineNumber);

        // then
        assertThat(findLine.id()).isEqualTo(testLineId);
        assertThat(findLine.nameKr()).isEqualTo(testLineNameKr);
        assertThat(findLine.nameEn()).isEqualTo(testLineNameEn);
        assertThat(findLine.nameJp()).isEqualTo(testLineNameJp);
        assertThat(findLine.number()).isEqualTo(testLineNumber);
    }

    @DisplayName("노선을 전체 조회한다(회사)")
    @Test
    public void findAllByCompany() throws Exception {
        // given
        LineEntity lineEntity = mock(LineEntity.class);
        CompanyEntity companyEntity = mock(CompanyEntity.class);

        Long testLineId = 1L;
        String testLineNameKr = "긴자선";
        String testLineNameEn = "Ginza Line";
        String testLineNameJp = "銀座線";
        String testLineNumber = "G";

        when(lineEntity.getId()).thenReturn(testLineId);
        when(lineEntity.getNameKr()).thenReturn(testLineNameKr);
        when(lineEntity.getNameEn()).thenReturn(testLineNameEn);
        when(lineEntity.getNameJp()).thenReturn(testLineNameJp);
        when(lineEntity.getNumber()).thenReturn(testLineNumber);
        when(lineEntity.getCompany()).thenReturn(companyEntity);

        Long testCompanyId = 2L;
        String testCompanyName = "Tokyo Metro";

        when(companyEntity.getId()).thenReturn(testCompanyId);

        LineRepository lineRepository = mock(LineRepository.class);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        when(lineRepository.findAllByCompany(companyEntity)).thenReturn(List.of(lineEntity));
        when(companyRepository.findById(testCompanyId)).thenReturn(Optional.of(companyEntity));

        // when
        LineService lineService = new LineServiceImpl(lineRepository, companyRepository);
        List<Line> lines = lineService.findAllByCompany(testCompanyId);

        // then
        assertThat(lines.size()).isEqualTo(1);
        assertThat(lines.get(0).id()).isEqualTo(testLineId);
    }
}