package com.github.gaboso.healthcareapi.service;

import com.github.gaboso.healthcareapi.domain.dto.CsvDto;
import com.github.gaboso.healthcareapi.domain.dto.MedicalRecordResponseDto;
import com.github.gaboso.healthcareapi.domain.entity.MedicalRecordEntity;
import com.github.gaboso.healthcareapi.exception.NotFoundException;
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

import java.util.Collections;
import java.util.List;
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
    void saveAll_ShouldCallRepositoryAndMapper() {
        Mockito.when(mapper.toMedicalRecordEntityList(ArgumentMatchers.anyList()))
            .thenReturn(List.of(new MedicalRecordEntity()));
        Mockito.when(repository.saveAll(ArgumentMatchers.anyList()))
            .thenReturn(List.of(new MedicalRecordEntity()));
        Mockito.when(mapper.toMedicalRecordResponseList(ArgumentMatchers.anyList()))
            .thenReturn(List.of(MedicalRecordResponseDto.builder().build()));

        List<CsvDto> csvDtoList = List.of(CsvDto.builder().code("xyz").build());
        service.saveAll(csvDtoList);

        Mockito.verify(mapper, Mockito.times(1)).toMedicalRecordEntityList(ArgumentMatchers.anyList());
        Mockito.verify(repository, Mockito.times(1)).saveAllAndFlush(ArgumentMatchers.anyList());
        Mockito.verify(mapper, Mockito.times(1)).toMedicalRecordResponseList(ArgumentMatchers.anyList());
    }

    @Test
    void fetchByCode_ShouldCallRepositoryAndMapper() throws NotFoundException {
        MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
        Mockito.when(repository.findByCode(ArgumentMatchers.anyString())).thenReturn(Optional.of(medicalRecordEntity));
        Mockito.when(mapper.toMedicalRecordResponse(ArgumentMatchers.any(MedicalRecordEntity.class)))
            .thenReturn(MedicalRecordResponseDto.builder().build());

        service.fetchByCode("1");

        Mockito.verify(repository, Mockito.times(1)).findByCode(ArgumentMatchers.anyString());
        Mockito.verify(mapper, Mockito.times(1)).toMedicalRecordResponse(ArgumentMatchers.any());
    }

    @Test
    void fetchAll_ShouldCallRepositoryAndMapper() {
        Mockito.when(repository.findAll()).thenReturn(Collections.emptyList());
        Mockito.when(mapper.toMedicalRecordResponseList(ArgumentMatchers.anyList())).thenReturn(Collections.emptyList());

        service.fetchAll();

        Mockito.verify(repository, Mockito.times(1)).findAll();
        Mockito.verify(mapper, Mockito.times(1)).toMedicalRecordResponseList(ArgumentMatchers.anyList());
    }

    @Test
    void deleteAll_ShouldCallRepository() {
        service.deleteAll();

        Mockito.verify(repository, Mockito.times(1)).deleteAllInBatch();
    }

}