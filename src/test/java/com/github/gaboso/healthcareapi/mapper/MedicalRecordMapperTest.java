package com.github.gaboso.healthcareapi.mapper;

import com.github.gaboso.healthcareapi.domain.dto.MedicalRecordResponseDto;
import com.github.gaboso.healthcareapi.domain.entity.MedicalRecordEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

class MedicalRecordMapperTest {

    private static MedicalRecordMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = Mappers.getMapper(MedicalRecordMapper.class);
    }

    @Test
    void toMedicalRecordResponse_ValidInput_IsProperlyMapped() {
        MedicalRecordEntity entity = buildValidEntity();
        MedicalRecordResponseDto expected = buildValidDto();
        MedicalRecordResponseDto result = mapper.toMedicalRecordResponse(entity);

        assertEqualsDto(expected, result);
    }

    @Test
    void toMedicalRecordResponse_NullInput_IsMappedAsNull() {
        MedicalRecordResponseDto result = mapper.toMedicalRecordResponse(null);

        Assertions.assertNull(result);
    }

    private MedicalRecordEntity buildValidEntity() {
        MedicalRecordEntity entity = new MedicalRecordEntity();
        entity.setSource("ZIB");
        entity.setCodeListCode("ZIB001");
        entity.setCode("271636001");
        entity.setDisplayValue("Polsslag regelmatig");
        entity.setFromDate(LocalDate.of(2022, 1, 7));
        entity.setSortingPriority(2);
        return entity;
    }

    private MedicalRecordResponseDto buildValidDto() {
        return MedicalRecordResponseDto.builder()
            .source("ZIB")
            .codeListCode("ZIB001")
            .code("271636001")
            .displayValue("Polsslag regelmatig")
            .sortingPriority(2)
            .fromDate("07-01-2022")
            .build();
    }

    private void assertEqualsDto(MedicalRecordResponseDto expected, MedicalRecordResponseDto result) {
        Assertions.assertEquals(expected.getLongDescription(), result.getLongDescription());
        Assertions.assertEquals(expected.getCode(), result.getCode());
        Assertions.assertEquals(expected.getCodeListCode(), result.getCodeListCode());
        Assertions.assertEquals(expected.getFromDate(), result.getFromDate());
        Assertions.assertEquals(expected.getToDate(), result.getToDate());
        Assertions.assertEquals(expected.getDisplayValue(), result.getDisplayValue());
        Assertions.assertEquals(expected.getSortingPriority(), result.getSortingPriority());
        Assertions.assertEquals(expected.getSource(), result.getSource());
    }
}