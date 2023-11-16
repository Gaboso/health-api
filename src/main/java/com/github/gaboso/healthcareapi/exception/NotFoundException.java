package com.github.gaboso.healthcareapi.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends Exception {

    public NotFoundException(String code) {
        super("Resource with code: " + code + " not found, check if the value is correct");
    }

}