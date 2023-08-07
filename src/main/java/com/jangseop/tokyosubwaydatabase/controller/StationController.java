package com.jangseop.tokyosubwaydatabase.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping
public class StationController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

}
