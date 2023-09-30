package com.shelterhub.controller;

import com.shelterhub.domain.model.MedicalRecord;
import com.shelterhub.dto.request.MedicalRecordRequest;
import com.shelterhub.dto.response.MedicalRecordResponse;
import com.shelterhub.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/v1/medical-record", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping
    public List<MedicalRecordResponse> getAll() {
        return medicalRecordService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<MedicalRecordResponse> getById(@PathVariable UUID id) {
        return medicalRecordService.getById(id);
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
    public ResponseEntity<MedicalRecord> delete(@PathVariable UUID id) {
        var medicalRecordOptional = medicalRecordService.deleteById(id);
        return ResponseEntity.of(medicalRecordOptional);
    }
}
