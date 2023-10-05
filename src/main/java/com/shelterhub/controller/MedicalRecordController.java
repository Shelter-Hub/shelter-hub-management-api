package com.shelterhub.controller;

import com.shelterhub.dto.request.MedicalRecordRequest;
import com.shelterhub.dto.response.MedicalRecordResponse;
import com.shelterhub.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/v1/medical-record", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping
    public ResponseEntity<List<MedicalRecordResponse>> getAll() {
        var medicalRecords = medicalRecordService.getAll();
        if (medicalRecords.isEmpty())
            return ResponseEntity.notFound()
                    .build();

        return ResponseEntity.ok(medicalRecords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordResponse> getById(@PathVariable UUID id) {
        var medicalRecordOptional = medicalRecordService.getById(id);
        return ResponseEntity
                .of(medicalRecordOptional);
    }

    @PostMapping
    public ResponseEntity<MedicalRecordResponse> create(@RequestBody MedicalRecordRequest medicalRecord) {
        var medicalResponse = medicalRecordService.create(medicalRecord);

        return ResponseEntity.ofNullable(medicalResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordResponse> update(
            @PathVariable UUID id,
            @RequestBody MedicalRecordRequest medicalRecord
    ) {
        var medicalRecordPersisted = medicalRecordService.update(medicalRecord, id);

        return ResponseEntity.ofNullable(medicalRecordPersisted);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable UUID id) {
        var medicalRecord = medicalRecordService.deleteById(id);
        if (medicalRecord.isPresent())
            return ResponseEntity
                    .noContent()
                    .build();

        return ResponseEntity
                .notFound()
                .build();
    }
}
