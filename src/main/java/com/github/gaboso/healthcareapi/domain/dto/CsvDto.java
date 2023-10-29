package com.github.gaboso.healthcareapi.domain.dto;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CsvDto {

    @CsvBindByPosition(position = 0)
    private String source;

    @CsvBindByPosition(position = 1)
    private String codeListCode;

    @NotBlank
    @CsvBindByPosition(position = 2)
    private String code;

    @CsvBindByPosition(position = 3)
    private String displayValue;

    @CsvBindByPosition(position = 4)
    private String longDescription;

    @CsvBindByPosition(position = 5)
    @CsvDate(value = "dd-MM-yyyy")
    private LocalDate fromDate;

    @CsvBindByPosition(position = 6)
    @CsvDate(value = "dd-MM-yyyy")
    private LocalDate toDate;

    @CsvBindByPosition(position = 7)
    private Integer sortingPriority;

}