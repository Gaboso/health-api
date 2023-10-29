package com.github.gaboso.healthcareapi.controller;

import com.github.gaboso.healthcareapi.domain.dto.MedicalRecordResponseDto;
import com.github.gaboso.healthcareapi.service.MedicalRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class MedicalRecordController {

    private final MedicalRecordService service;

    public MedicalRecordController(MedicalRecordService service) {
        this.service = service;
    }

    @GetMapping("/fetch/{code}")
    public ResponseEntity<MedicalRecordResponseDto> fetchByCode(@PathVariable("code") String code) {
        log.debug("REST request to find a medical record by code");

        MedicalRecordResponseDto dto = service.fetchByCode(code);
        return ResponseEntity.ok(dto);
    }

}
