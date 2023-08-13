package com.jangseop.tokyosubwaydatabase.integration;

import com.jangseop.tokyosubwaydatabase.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Setup {

    @Autowired private StationRepository stationRepository;

    @Autowired private CompanyRepository companyRepository;

    @Autowired private LineRepository lineRepository;

    @Autowired private FarePolicyRepository farePolicyRepository;

    @Autowired private LineStationRepository lineStationRepository;

    public void companySetup() {

    }
}
