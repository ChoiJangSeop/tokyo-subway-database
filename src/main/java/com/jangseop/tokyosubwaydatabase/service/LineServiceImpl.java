package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Line;
import com.jangseop.tokyosubwaydatabase.entity.CompanyEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import com.jangseop.tokyosubwaydatabase.exception.illegal_format.IllegalLineNumberFormatException;
import com.jangseop.tokyosubwaydatabase.exception.not_found.CompanyNotFoundException;
import com.jangseop.tokyosubwaydatabase.exception.not_found.LineNotFoundException;
import com.jangseop.tokyosubwaydatabase.exception.duplicated.LineNumberDuplicationException;
import com.jangseop.tokyosubwaydatabase.repository.CompanyRepository;
import com.jangseop.tokyosubwaydatabase.repository.LineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LineServiceImpl implements LineService {

    private final LineRepository lineRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public Line create(LineCreateDto dto) {
        LineEntity newLine = LineEntity.of(dto.nameKr(), dto.nameEn(), dto.nameJp(), dto.number());

        validateLineNumberDuplication(dto.number());

        lineRepository.save(newLine);

        CompanyEntity company = companyRepository.findById(dto.companyId())
                .orElseThrow(() -> new CompanyNotFoundException(dto.companyId()));

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
    @Transactional(readOnly = true)
    public List<Line> findAllByCompany(Long companyId) {
        CompanyEntity company = companyRepository.getReferenceById(companyId);
//        CompanyEntity company = companyRepository.findById(companyId)
//                .orElseThrow(() -> new CompanyNotFoundException(companyId));

        // domain 시인성이 떨어짐
//        return company.getLines().stream()
//                .map(Line::of)
//                .collect(Collectors.toList());

        return lineRepository.findAllByCompany(company).stream()
                .map(Line::of)
                .toList();
    }

    @Override
    public List<Line> findAll() {
        return lineRepository.findAll().stream()
                .map(Line::of)
                .toList();
    }

    private void validateLineNumberDuplication(String lineNumber) {
        if (lineRepository.findByNumber(lineNumber).isPresent()) {
            throw new LineNumberDuplicationException(lineNumber);
        }
    }


}
