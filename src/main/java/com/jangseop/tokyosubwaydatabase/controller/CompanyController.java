package com.jangseop.tokyosubwaydatabase.controller;

import com.jangseop.tokyosubwaydatabase.controller.dto.request.CompanyCreateRequest;
import com.jangseop.tokyosubwaydatabase.controller.dto.response.CompanyResponse;
import com.jangseop.tokyosubwaydatabase.domain.Company;
import com.jangseop.tokyosubwaydatabase.service.CompanyService;
import com.jangseop.tokyosubwaydatabase.util.createdto.CompanyCreateDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:8081")
public class CompanyController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CompanyService companyService;

    @PostMapping("/company")
    public ResponseEntity<CompanyResponse> newOne(@RequestBody CompanyCreateRequest request) {
        CompanyCreateDto dto = CompanyCreateDto.of(request.name());
        Company company = companyService.create(dto);

        return new ResponseEntity<>(CompanyResponse.of(company), HttpStatus.OK);
    }
}
