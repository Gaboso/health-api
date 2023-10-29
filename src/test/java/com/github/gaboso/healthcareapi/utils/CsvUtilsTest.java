package com.github.gaboso.healthcareapi.utils;

import com.github.gaboso.healthcareapi.domain.dto.CsvDto;
import com.github.gaboso.healthcareapi.exception.EmptyFileException;
import com.github.gaboso.healthcareapi.exception.UnsupportedFileException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

class CsvUtilsTest {

    private CsvUtils csvUtils;
    private AutoCloseable closeable;

    @Mock
    private MultipartFile file;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        csvUtils = new CsvUtils();
    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  "})
    void testGetDataFromFile_EmptyFile_ReturnsEmptyList(String csvText) throws IOException {
        byte[] csvData = csvText.getBytes();
        MockMultipartFile file = new MockMultipartFile("csvFile", "invalidFile.csv", "text/csv", csvData);

        List<CsvDto> result = csvUtils.getDataFromFile(file);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetDataFromFile_ValidFile_ReturnsCsvDtoList() throws IOException {
        byte[] csvData = "\"source\",\"codeListCode\",\"code\",\"displayValue\",\"longDescription\",\"fromDate\",\"toDate\",\"sortingPriority\"\n\"ZIB\",\"ZIB003\",\"276885007\",\"Kern temperatuur\",\"Kern temperatuur (invasief gemeten)\",\"01-01-2023\",\"01-01-2024\",\"1\"".getBytes();
        MockMultipartFile file = new MockMultipartFile("csvFile", "test.csv", "text/csv", csvData);

        List<CsvDto> result = csvUtils.getDataFromFile(file);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        CsvDto csvDto = result.get(0);
        Assertions.assertEquals("ZIB", csvDto.getSource());
        Assertions.assertEquals("ZIB003", csvDto.getCodeListCode());
        Assertions.assertEquals("276885007", csvDto.getCode());
        Assertions.assertEquals("Kern temperatuur", csvDto.getDisplayValue());
        Assertions.assertEquals("Kern temperatuur (invasief gemeten)", csvDto.getLongDescription());
        Assertions.assertEquals(LocalDate.of(2023, 1, 1), csvDto.getFromDate());
        Assertions.assertEquals(LocalDate.of(2024, 1, 1), csvDto.getToDate());
        Assertions.assertEquals(1, csvDto.getSortingPriority());
    }

    @Test
    void validateFile_EmptyFile_ThrowsEmptyFileException() {
        Mockito.when(file.isEmpty()).thenReturn(true);
        Assertions.assertThrows(EmptyFileException.class, () -> csvUtils.validateFile(file));
    }

    @ParameterizedTest
    @ValueSource(strings = {"text/xml", "text/css", "image/gif", "application/json"})
    void validateFile_InvalidContentType_ThrowsUnsupportedFileException(String invalidContentType) {
        Mockito.when(file.isEmpty()).thenReturn(false);
        Mockito.when(file.getContentType()).thenReturn(invalidContentType);
        Assertions.assertThrows(UnsupportedFileException.class, () -> csvUtils.validateFile(file));
    }

    @Test
    void validateFile_validContentType_DoNotThrowsUnsupportedFileException() {
        Mockito.when(file.isEmpty()).thenReturn(false);
        Mockito.when(file.getContentType()).thenReturn("text/csv");
        Assertions.assertDoesNotThrow(() -> csvUtils.validateFile(file));
    }

}