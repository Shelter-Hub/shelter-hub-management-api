package com.shelterhub.controller;

import com.shelterhub.dto.MedicalRecordDTO;
import com.shelterhub.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/medical-record", produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    private static Logger log = LoggerFactory.getLogger(MedicalRecordController.class);

    @GetMapping
    public List<MedicalRecordDTO> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }
    @GetMapping("/{id}")
    public Optional<MedicalRecordDTO> getMedicalRecordsById(@PathVariable UUID id) {
        return medicalRecordService.getMedicalRecordById(id);
    }
    @PostMapping
    public void createMedicalRecord(@RequestBody MedicalRecordDTO medicalRecord){
        medicalRecordService.create(medicalRecord);
    }
    @PutMapping("/{id}")
    public void updateMedicalRecord(@PathVariable UUID id,
                                    @RequestBody MedicalRecordDTO medicalRecord){
        if(id != null) medicalRecordService.update(medicalRecord, id);
    }
    @DeleteMapping("/{id}")
    public void deleteMedicalRecord(@PathVariable UUID id){
        medicalRecordService.delete(id);
    }
}
