package com.github.gaboso.healthcareapi.controller;

import com.github.gaboso.healthcareapi.domain.dto.CsvDto;
import com.github.gaboso.healthcareapi.domain.dto.MedicalRecordResponseDto;
import com.github.gaboso.healthcareapi.exception.template.ErrorTemplate;
import com.github.gaboso.healthcareapi.service.MedicalRecordService;
import com.github.gaboso.healthcareapi.utils.CsvUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Medical Record", description = "Medical Records Management")
@RestController
@Slf4j
@RequestMapping("/api/v1")
public class MedicalRecordController {

    private final MedicalRecordService service;
    private final CsvUtils csvUtils;

    public MedicalRecordController(MedicalRecordService service, CsvUtils csvUtils) {
        this.service = service;
        this.csvUtils = csvUtils;
    }

    @Operation(
        summary = "Upload a .csv file",
        description = "This endpoint allows users to securely upload a .CSV file containing medical records. Upon successful upload, the system processes the data and returns a list of medical record in response."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Records from uploaded file was saved with success", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = MedicalRecordResponseDto.class)))
        }),
        @ApiResponse(responseCode = "400", description = "Empty file provided", content = {
            @Content(schema = @Schema(implementation = ErrorTemplate.class))
        }),
        @ApiResponse(responseCode = "409", description = "Resource already exists", content = {
            @Content(schema = @Schema(implementation = ErrorTemplate.class))
        }),
        @ApiResponse(responseCode = "415", description = "Wrong file type", content = {
            @Content(schema = @Schema(implementation = ErrorTemplate.class))
        })
    })
    @PostMapping(path = {"/upload"}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<MedicalRecordResponseDto>> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        log.debug("REST request to upload csv file data");

        csvUtils.validateFile(file);
        List<CsvDto> dtoList = csvUtils.getDataFromFile(file);
        List<MedicalRecordResponseDto> savedDtoList = service.saveAll(dtoList);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDtoList);
    }

    @Operation(
        summary = "Find Medical Record by Code",
        description = "This endpoint enables users to search for a specific medical record using a unique code. Upon a successful search, the system retrieves and returns the corresponding Medical Record as the response."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Medical Record", content = {
            @Content(schema = @Schema(implementation = MedicalRecordResponseDto.class))
        })
    })
    @GetMapping("/fetch/{code}")
    public ResponseEntity<MedicalRecordResponseDto> fetchByCode(@PathVariable("code") String code) {
        log.debug("REST request to find a medical record by code");

        MedicalRecordResponseDto dto = service.fetchByCode(code);
        return ResponseEntity.ok(dto);
    }

    @Operation(
        summary = "Find all Medical Records",
        description = "This endpoint enables users to search for all medical records. Upon a successful search, the system retrieves and returns a list of medical record as the response."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Medical Records", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = MedicalRecordResponseDto.class)))
        })
    })
    @GetMapping("/fetch/all")
    public ResponseEntity<List<MedicalRecordResponseDto>> fetchAll() {
        log.debug("REST request to get all medical records");

        List<MedicalRecordResponseDto> dtoList = service.fetchAll();
        return ResponseEntity.ok(dtoList);
    }

    @Operation(
        summary = "Delete all Medical Records",
        description = "This endpoint provides the capability to delete all stored medical records. Upon a successful request, the system will permanently remove all records from the database."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Medical Records removed")
    })
    @DeleteMapping("/delete/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteAll() {
        log.debug("REST request to delete all medical records");

        service.deleteAll();
        return ResponseEntity.noContent().build();
    }

}
