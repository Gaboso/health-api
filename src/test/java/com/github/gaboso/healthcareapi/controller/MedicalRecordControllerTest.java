package com.github.gaboso.healthcareapi.controller;

import com.github.gaboso.healthcareapi.domain.dto.MedicalRecordResponseDto;
import com.github.gaboso.healthcareapi.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class MedicalRecordControllerTest {

    @Mock
    private MedicalRecordService service;

    @InjectMocks
    private MedicalRecordController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new MedicalRecordController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void fetchByCode_FetchMedicalRecordsByExistingCode_return200() throws Exception {
        MedicalRecordResponseDto responseDto = MedicalRecordResponseDto.builder().source("ZIB")
            .codeListCode("ZIB001")
            .code("61086009")
            .longDescription("Polsslag onregelmatig")
            .fromDate("01-01-2022")
            .sortingPriority(2)
            .build();

        Mockito.when(service.fetchByCode(ArgumentMatchers.any()))
            .thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fetch/271636001")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

}