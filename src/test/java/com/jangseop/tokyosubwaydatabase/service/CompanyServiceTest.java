package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Company;
import com.jangseop.tokyosubwaydatabase.entity.CompanyEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import com.jangseop.tokyosubwaydatabase.exception.CompanyNameDuplicatedException;
import com.jangseop.tokyosubwaydatabase.exception.CompanyNotFoundException;
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
class CompanyServiceTest {


    // TODO 생성 관련 테스트 코드 어떻게?
    // 통합 테스트 필요
    @DisplayName("새로운 회사를 생성한다")
    @Test
    public void create() throws Exception {
        // given

        // in-memory h2
        // docker - mysql

        // when

        // then
        fail();
    }

    @DisplayName("회사 생성시, 이름이 중복될 경우 예외를 던진다")
    @Test
    public void nameDuplicationError() throws Exception {
        // given
        CompanyEntity companyEntity = mock(CompanyEntity.class);
        CompanyRepository companyRepository = mock(CompanyRepository.class);
        LineRepository lineRepository = mock(LineRepository.class);

        String testCompanyName = "test";
        when(companyRepository.findByName(testCompanyName)).thenReturn(Optional.of(companyEntity));

        // when
        CompanyService companyService = new CompanyServiceImpl(companyRepository, lineRepository);

        // then
        assertThatThrownBy(() -> companyService.create(testCompanyName))
                .isInstanceOf(CompanyNameDuplicatedException.class);
    }

    @DisplayName("회사 정보를 조회한다.")
    @Test
    public void findByIdTest() throws Exception {
        // given
        CompanyEntity companyEntity = mock(CompanyEntity.class);
        LineEntity lineEntity = mock(LineEntity.class);

        CompanyRepository companyRepository = mock(CompanyRepository.class);
        LineRepository lineRepository = mock(LineRepository.class);

        String testCompanyName = "Tokyo Metro";
        Long testCompanyId = 1L;

        String testLineNameKr = "긴자선";
        String testLineNameEn = "Ginza Line";
        String testLineNameJp = "銀座線";
        Long testLineId = 2L;

        when(companyEntity.getId()).thenReturn(testCompanyId);
        when(companyEntity.getName()).thenReturn(testCompanyName);
        when(companyEntity.getLines()).thenReturn(List.of(lineEntity));

        when(lineEntity.getId()).thenReturn(testLineId);
        when(lineEntity.getNameJp()).thenReturn(testLineNameJp);
        when(lineEntity.getNameEn()).thenReturn(testLineNameEn);
        when(lineEntity.getNameKr()).thenReturn(testLineNameKr);
        when(lineEntity.getCompany()).thenReturn(companyEntity);

        when(companyRepository.findById(testCompanyId)).thenReturn(Optional.of(companyEntity));

        // when
        CompanyService companyService = new CompanyServiceImpl(companyRepository, lineRepository);
        Company findCompany = companyService.findById(testCompanyId);

        // then
        assertThat(findCompany.id()).isEqualTo(testCompanyId);
        assertThat(findCompany.name()).isEqualTo(testCompanyName);
        assertThat(findCompany.lines().size()).isEqualTo(1);
        assertThat(findCompany.lines().get(0).nameEn()).isEqualTo(testLineNameEn);

    }



}