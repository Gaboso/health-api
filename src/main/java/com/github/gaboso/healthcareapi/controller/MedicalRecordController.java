package com.github.gaboso.healthcareapi.controller;

import com.github.gaboso.healthcareapi.domain.dto.CsvDto;
import com.github.gaboso.healthcareapi.domain.dto.MedicalRecordResponseDto;
import com.github.gaboso.healthcareapi.service.MedicalRecordService;
import com.github.gaboso.healthcareapi.utils.CsvUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class MedicalRecordController {

    private final MedicalRecordService service;
    private final CsvUtils csvUtils;

    public MedicalRecordController(MedicalRecordService service, CsvUtils csvUtils) {
        this.service = service;
        this.csvUtils = csvUtils;
    }

    @PostMapping(path = {"/upload"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<MedicalRecordResponseDto>> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        log.debug("REST request to upload csv file data");

        csvUtils.validateFile(file);
        List<CsvDto> dtoList = csvUtils.getDataFromFile(file);
        List<MedicalRecordResponseDto> savedDtoList = service.saveAll(dtoList);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDtoList);
    }

    @GetMapping("/fetch/{code}")
    public ResponseEntity<MedicalRecordResponseDto> fetchByCode(@PathVariable("code") String code) {
        log.debug("REST request to find a medical record by code");

        MedicalRecordResponseDto dto = service.fetchByCode(code);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/fetch/all")
    public ResponseEntity<List<MedicalRecordResponseDto>> fetchAll() {
        log.debug("REST request to get all medical records");

        List<MedicalRecordResponseDto> dtoList = service.fetchAll();
        return ResponseEntity.ok(dtoList);
    }

}
