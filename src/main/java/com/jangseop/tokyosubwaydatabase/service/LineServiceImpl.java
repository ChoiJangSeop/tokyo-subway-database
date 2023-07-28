package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Line;
import com.jangseop.tokyosubwaydatabase.entity.CompanyEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import com.jangseop.tokyosubwaydatabase.exception.CompanyNotFoundException;
import com.jangseop.tokyosubwaydatabase.exception.LineNotFoundException;
import com.jangseop.tokyosubwaydatabase.repository.CompanyRepository;
import com.jangseop.tokyosubwaydatabase.repository.LineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LineServiceImpl implements LineService {

    private final LineRepository lineRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public Line create(Long companyId, String nameKr, String nameEn, String nameJp, String number) {
        LineEntity newLine = LineEntity.of(nameKr, nameEn, nameJp, number);

        // TODO validation method

        lineRepository.save(newLine);

        CompanyEntity company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(companyId));

        newLine.setCompany(company);

        return Line.of(newLine);
    }

    @Override
    public Line findById(Long id) {
        return lineRepository.findById(id)
                .map(Line::of)
                .orElseThrow(() -> new LineNotFoundException(id));
    }

    @Override
    public Line findByNumber(String number) {
        return lineRepository.findByNumber(number)
                .map(Line::of)
                .orElseThrow(() -> new LineNotFoundException(number));
    }

    @Override
    public List<Line> findAllByCompany(Long companyId) {
        CompanyEntity company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException(companyId));

        return lineRepository.findAllByCompany(company).stream()
                .map(Line::of)
                .collect(Collectors.toList());
    }
}