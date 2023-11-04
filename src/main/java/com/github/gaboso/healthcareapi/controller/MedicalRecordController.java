package com.github.gaboso.healthcareapi.controller;

import com.github.gaboso.healthcareapi.api.MedicalRecordApi;
import com.github.gaboso.healthcareapi.domain.dto.CsvDto;
import com.github.gaboso.healthcareapi.domain.dto.MedicalRecordResponseDto;
import com.github.gaboso.healthcareapi.exception.EmptyFileException;
import com.github.gaboso.healthcareapi.exception.UnsupportedFileException;
import com.github.gaboso.healthcareapi.service.MedicalRecordService;
import com.github.gaboso.healthcareapi.utils.CsvUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
public class MedicalRecordController implements MedicalRecordApi {

    private final MedicalRecordService service;
    private final CsvUtils csvUtils;

    public MedicalRecordController(MedicalRecordService service, CsvUtils csvUtils) {
        this.service = service;
        this.csvUtils = csvUtils;
    }

    @Override
    public ResponseEntity<List<MedicalRecordResponseDto>> uploadFile(@RequestParam("file") MultipartFile file) throws EmptyFileException, IOException, UnsupportedFileException {
        csvUtils.validateFile(file);
        List<CsvDto> dtoList = csvUtils.getDataFromFile(file);
        List<MedicalRecordResponseDto> savedDtoList = service.saveAll(dtoList);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDtoList);
    }

    @Override
    public ResponseEntity<MedicalRecordResponseDto> fetchByCode(@PathVariable("code") String code) {
        MedicalRecordResponseDto dto = service.fetchByCode(code);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<List<MedicalRecordResponseDto>> fetchAll() {
        List<MedicalRecordResponseDto> dtoList = service.fetchAll();
        return ResponseEntity.ok(dtoList);
    }

    @Override
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
