package com.github.gaboso.healthcareapi.controller;

import com.github.gaboso.healthcareapi.domain.dto.CsvDto;
import com.github.gaboso.healthcareapi.domain.dto.MedicalRecordResponseDto;
import com.github.gaboso.healthcareapi.service.MedicalRecordService;
import com.github.gaboso.healthcareapi.utils.CsvUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class MedicalRecordControllerTest {

    @Mock
    private MedicalRecordService service;

    @Mock
    private CsvUtils csvUtils;

    @InjectMocks
    private MedicalRecordController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new MedicalRecordController(service, csvUtils);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void uploadFile_UploadValidCsv_returns201() throws Exception {
        List<CsvDto> csvDtoList = new ArrayList<>();
        csvDtoList.add(new CsvDto("ZIB", "ZIB001", "61086009", "Polsslag onregelmatig", null, LocalDate.now(), null, 2));
        csvDtoList.add(new CsvDto("ZIB", "ZIB001", "271636001", "Polsslag regelmatig", "The long description is necessary", LocalDate.now(), null, 1));

        Mockito.when(csvUtils.getDataFromFile(ArgumentMatchers.any(MultipartFile.class)))
            .thenReturn(csvDtoList);

        Mockito.when(service.saveAll(ArgumentMatchers.anyList()))
            .thenReturn(ArgumentMatchers.anyList());

        InputStream is = controller.getClass()
            .getClassLoader()
            .getResourceAsStream("exercise.csv");
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "exercise.csv", "multipart/form-data", is);
        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/api/v1/upload")
                .file("file", mockMultipartFile.getBytes()))
            .andExpect(MockMvcResultMatchers.status().isCreated());
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

    @Test
    void fetchAll_FetchAllMedicalRecords_returns200() throws Exception {
        MedicalRecordResponseDto firstRecord = MedicalRecordResponseDto.builder().source("ZIB")
            .codeListCode("ZIB001")
            .code("61086009")
            .displayValue("Polsslag onregelmatig")
            .fromDate("01-01-2022")
            .sortingPriority(2)
            .build();

        MedicalRecordResponseDto secondRecord = MedicalRecordResponseDto.builder().source("ZIB")
            .codeListCode("ZIB001")
            .code("271636001")
            .displayValue("Polsslag regelmatig")
            .longDescription("The long description is necessary")
            .fromDate("01-01-2022")
            .sortingPriority(1)
            .build();

        List<MedicalRecordResponseDto> responseDtoList = List.of(firstRecord, secondRecord);

        Mockito.when(service.fetchAll())
            .thenReturn(responseDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fetch/all")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

}