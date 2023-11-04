package com.github.gaboso.healthcareapi.controller.exception;

import com.github.gaboso.healthcareapi.exception.EmptyFileException;
import com.github.gaboso.healthcareapi.exception.UnsupportedFileException;
import com.github.gaboso.healthcareapi.exception.template.ErrorTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorTemplate> handleException(Exception e) {
        log.error("Unexpected Exception: ", e);
        return createErrorTemplateResponseEntity(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyFileException.class)
    public ResponseEntity<ErrorTemplate> handler(EmptyFileException e) {
        log.error("Handling Empty File Exception", e);
        return createErrorTemplateResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorTemplate> handler(HttpMediaTypeNotSupportedException e) {
        log.error("Handling Content-Type not supported Exception", e);
        return createErrorTemplateResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(UnsupportedFileException.class)
    public ResponseEntity<ErrorTemplate> handler(UnsupportedFileException e) {
        log.error("Handling Unsupported File Exception", e);
        return createErrorTemplateResponseEntity(e, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorTemplate> handler(DataIntegrityViolationException e) {
        log.error("Handling Data Integrity Violation Exception", e);
        ErrorTemplate build = new ErrorTemplate(HttpStatus.CONFLICT.name(), "An error occurred when saving the medical records. Please review the file to ensure that the records are valid and have not been submitted before.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(build);
    }

    protected ResponseEntity<ErrorTemplate> createErrorTemplateResponseEntity(Exception exception, HttpStatus httpStatus) {
        String message = exception.getMessage();
        ErrorTemplate build = new ErrorTemplate(httpStatus.name(), message);
        return ResponseEntity.status(httpStatus).body(build);
    }

}
