package com.github.gaboso.healthcareapi.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "MedicalRecord")
public class MedicalRecordResponseDto {

    @Schema(example = "ZIB")
    private String source;

    @Schema(example = "ZIB001")
    private String codeListCode;

    @Schema(example = "307047009")
    private String code;

    @Schema(example = "Orale temperatuur")
    private String displayValue;

    @Schema(example = "Orale temperatuur (onder de tong)")
    private String longDescription;

    @Schema(example = "01-01-2023")
    private String fromDate;

    @Schema(example = "01-01-2024")
    private String toDate;

    @Schema(example = "1")
    private Integer sortingPriority;

}
