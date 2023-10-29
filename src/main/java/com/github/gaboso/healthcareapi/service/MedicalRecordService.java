package com.github.gaboso.healthcareapi.service;

import com.github.gaboso.healthcareapi.domain.dto.MedicalRecordResponseDto;
import com.github.gaboso.healthcareapi.domain.entity.MedicalRecordEntity;
import com.github.gaboso.healthcareapi.mapper.MedicalRecordMapper;
import com.github.gaboso.healthcareapi.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository repository;
    private final MedicalRecordMapper mapper;

    public MedicalRecordService(MedicalRecordRepository repository, MedicalRecordMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public MedicalRecordResponseDto fetchByCode(String code) {
        MedicalRecordEntity entity = repository.findByCode(code).orElse(null);

        return mapper.toMedicalRecordResponse(entity);
    }

    public List<MedicalRecordResponseDto> fetchAll() {
        List<MedicalRecordEntity> entityList = repository.findAll();
        return mapper.toMedicalRecordResponseList(entityList);
    }

}
