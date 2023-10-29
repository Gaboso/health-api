package com.github.gaboso.healthcareapi.service;

import com.github.gaboso.healthcareapi.domain.dto.CsvDto;
import com.github.gaboso.healthcareapi.domain.dto.MedicalRecordResponseDto;
import com.github.gaboso.healthcareapi.domain.entity.MedicalRecordEntity;
import com.github.gaboso.healthcareapi.mapper.MedicalRecordMapper;
import com.github.gaboso.healthcareapi.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository repository;
    private final MedicalRecordMapper mapper;

    public MedicalRecordService(MedicalRecordRepository repository, MedicalRecordMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public List<MedicalRecordResponseDto> saveAll(List<CsvDto> dtoList) {
        List<MedicalRecordEntity> entityList = mapper.toMedicalRecordEntityList(dtoList);
        List<MedicalRecordEntity> savedList = repository.saveAll(entityList);
        return mapper.toMedicalRecordResponseList(savedList);
    }

    public MedicalRecordResponseDto fetchByCode(String code) {
        MedicalRecordEntity entity = repository.findByCode(code).orElse(null);

        return mapper.toMedicalRecordResponse(entity);
    }

    public List<MedicalRecordResponseDto> fetchAll() {
        List<MedicalRecordEntity> entityList = repository.findAll();
        return mapper.toMedicalRecordResponseList(entityList);
    }

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

}
