package com.github.gaboso.healthcareapi.exception;

public class UnsupportedFileException extends Exception {

    public UnsupportedFileException() {
        super("Only supported file type is text/csv");
    }
}