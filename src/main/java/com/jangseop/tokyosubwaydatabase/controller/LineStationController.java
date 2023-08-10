package com.jangseop.tokyosubwaydatabase.controller;

import com.jangseop.tokyosubwaydatabase.service.LineStationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping
public class LineStationController {

    private final LineStationService lineStationService;
}
