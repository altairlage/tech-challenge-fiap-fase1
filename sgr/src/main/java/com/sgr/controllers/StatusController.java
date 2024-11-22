package com.sgr.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/status")
public class StatusController {
    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

    // http://localhost:8080/status
    @GetMapping
    public ResponseEntity<Void> findAluguel() {
        logger.info("invocado /status");
        return ResponseEntity.status(200).build();
    }
}
