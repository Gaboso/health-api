package com.github.gaboso.healthcareapi.utils;

import com.github.gaboso.healthcareapi.domain.dto.CsvDto;
import com.github.gaboso.healthcareapi.exception.EmptyFileException;
import com.github.gaboso.healthcareapi.exception.UnsupportedFileException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Slf4j
@Component
public class CsvUtils {

    public List<CsvDto> getDataFromFile(MultipartFile file) throws IOException {
        log.debug("Starting the extraction data from csv [{}]", file.getName());
        try (
            InputStream inputStream = file.getInputStream();
            Reader reader = new InputStreamReader(inputStream)
        ) {
            CsvToBean<CsvDto> csvToBean = new CsvToBeanBuilder<CsvDto>(reader)
                .withSkipLines(1)
                .withType(CsvDto.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
            List<CsvDto> csvDtoList = csvToBean.parse();

            log.debug("Data extracted from csv with success. Items found: {}", csvDtoList.size());
            return csvDtoList;
        } catch (IllegalStateException e) {
            log.error("Error while parsing data from csv", e);
            throw e;
        } catch (IOException e) {
            log.error("Error while loading/reading csv", e);
            throw e;
        }
    }

    public void validateFile(MultipartFile file) throws EmptyFileException, UnsupportedFileException {
        if (file.isEmpty()) {
            log.error("The file [{}] is empty", file.getName());
            throw new EmptyFileException();
        }

        if (!"text/csv".equals(file.getContentType())) {
            log.error("The file [{}] is [{}] but the expect type was text/csv", file.getName(), file.getContentType());
            throw new UnsupportedFileException();
        }
        log.debug("The file uploaded is a csv");
    }

}
