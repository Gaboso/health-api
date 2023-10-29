package com.github.gaboso.healthcareapi.mapper;

import com.github.gaboso.healthcareapi.domain.dto.MedicalRecordResponseDto;
import com.github.gaboso.healthcareapi.domain.entity.MedicalRecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalRecordMapper {

    @Mapping(target = "fromDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "toDate", dateFormat = "dd-MM-yyyy")
    MedicalRecordResponseDto toMedicalRecordResponse(MedicalRecordEntity entity);

    List<MedicalRecordResponseDto> toMedicalRecordResponseList(List<MedicalRecordEntity> entityList);

}