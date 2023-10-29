package com.github.gaboso.healthcareapi.exception.template;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorTemplate(@Schema(example = "XXX") String errorCode,
                            @Schema(example = "An error occurred") String errorMessage) {

}