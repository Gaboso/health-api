package com.github.gaboso.healthcareapi.controller.exception;


import com.github.gaboso.healthcareapi.exception.EmptyFileException;
import com.github.gaboso.healthcareapi.exception.UnsupportedFileException;
import com.github.gaboso.healthcareapi.exception.template.ErrorTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    public void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handler_EmptyFileException_ReturnsResponseEntityErrorTemplate() {
        EmptyFileException exception = new EmptyFileException();

        ResponseEntity<ErrorTemplate> responseEntity = globalExceptionHandler.handler(exception);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals("File is empty", responseEntity.getBody().errorMessage());
    }

    @Test
    void handler_UnsupportedFileException_ReturnsResponseEntityErrorTemplate() {
        UnsupportedFileException exception = new UnsupportedFileException();

        ResponseEntity<ErrorTemplate> responseEntity = globalExceptionHandler.handler(exception);

        Assertions.assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals("Only supported file type is text/csv", responseEntity.getBody().errorMessage());
    }

    @Test
    void handler_DataIntegrityViolationException_ReturnsResponseEntityErrorTemplate() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Already existing unique key");

        ResponseEntity<ErrorTemplate> responseEntity = globalExceptionHandler.handler(exception);

        Assertions.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals("An error occurred when saving the medical records. Please review the file to ensure that the records are valid and have not been submitted before.",
            responseEntity.getBody().errorMessage());
    }

    @Test
    void handleException_AnyException_ReturnsResponseEntity() {
        Exception exception = new Exception("Generic Error");

        ResponseEntity<ErrorTemplate> responseEntity = globalExceptionHandler.handleException(exception);

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals("Generic Error", responseEntity.getBody().errorMessage());
    }

}