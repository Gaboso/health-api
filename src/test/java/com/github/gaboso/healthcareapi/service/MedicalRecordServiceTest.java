package com.github.gaboso.healthcareapi.service;

import com.github.gaboso.healthcareapi.domain.dto.MedicalRecordResponseDto;
import com.github.gaboso.healthcareapi.domain.entity.MedicalRecordEntity;
import com.github.gaboso.healthcareapi.mapper.MedicalRecordMapper;
import com.github.gaboso.healthcareapi.repository.MedicalRecordRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository repository;
    @Mock
    private MedicalRecordMapper mapper;

    @InjectMocks
    private MedicalRecordService service;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void fetchByCode_ShouldCallRepositoryAndMapper() {
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
        Mockito.when(repository.findByCode(ArgumentMatchers.anyString())).thenReturn(Optional.of(medicalRecordEntity));
        Mockito.when(mapper.toMedicalRecordResponse(ArgumentMatchers.any(MedicalRecordEntity.class)))
            .thenReturn(MedicalRecordResponseDto.builder().build());

        service.fetchByCode("1");

        Mockito.verify(repository, Mockito.times(1)).findByCode(ArgumentMatchers.anyString());
        Mockito.verify(mapper, Mockito.times(1)).toMedicalRecordResponse(ArgumentMatchers.any());
    }
}