package com.github.gaboso.healthcareapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_medical_record")
public class MedicalRecordEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "source", nullable = false)
    private String source;

    @Column(name = "code_list_code", nullable = false)
    private String codeListCode;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "display_value", nullable = false)
    private String displayValue;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "from_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate fromDate;

    @Column(name = "to_date")
    @Temporal(TemporalType.DATE)
    private LocalDate toDate;

    @Column(name = "sorting_priority")
    private Integer sortingPriority;

}
