package com.jangseop.tokyosubwaydatabase.service;

import com.jangseop.tokyosubwaydatabase.domain.Company;
import com.jangseop.tokyosubwaydatabase.domain.Line;
import com.jangseop.tokyosubwaydatabase.entity.CompanyEntity;
import com.jangseop.tokyosubwaydatabase.entity.LineEntity;
import com.jangseop.tokyosubwaydatabase.exception.CompanyNameDuplicatedException;
import com.jangseop.tokyosubwaydatabase.exception.CompanyNotFoundException;
import com.jangseop.tokyosubwaydatabase.repository.CompanyRepository;
import com.jangseop.tokyosubwaydatabase.repository.LineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// interface -> mock data test -> impl
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final LineRepository lineRepository;

    @Override
    public Company create(String name) {

        // validation method
        validateNameDuplication(name);

        CompanyEntity newCompany = CompanyEntity.of(name);
        companyRepository.save(newCompany);

        return Company.of(newCompany);
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findById(id)
                .map(Company::of)
                .orElseThrow(() -> new CompanyNotFoundException(id));
    }

    @Override
    public Company findByName(String name) {
        return companyRepository.findByName(name)
                .map(Company::of)
                .orElseThrow(() -> new CompanyNotFoundException(name));
    }

    /**
     * validation method
     */
    public void validateNameDuplication(String name) {
        if (companyRepository.findByName(name).isPresent()) {
            throw new CompanyNameDuplicatedException(name);
        }
    }
}
