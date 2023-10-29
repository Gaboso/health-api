package com.github.gaboso.healthcareapi.exception;

public class EmptyFileException extends Exception {

    public EmptyFileException() {
        super("File is empty");
    }
}