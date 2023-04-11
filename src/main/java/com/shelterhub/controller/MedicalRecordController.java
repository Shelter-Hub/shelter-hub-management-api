package com.shelterhub.controller;

import com.shelterhub.dto.MedicalRecordDTO;
import com.shelterhub.service.MedicalRecordFacade;
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
    private MedicalRecordFacade medicalRecordFacade;

    @GetMapping
    public List<MedicalRecordDTO> getAllMedicalRecords() {
        return medicalRecordFacade.getAllMedicalRecords();
    }
    @GetMapping("/{id}")
    public Optional<MedicalRecordDTO> getMedicalRecordsById(@PathVariable UUID id) {
        return medicalRecordFacade.getMedicalRecordById(id);
    }
    @PostMapping
    public void createMedicalRecord(@RequestBody MedicalRecordDTO medicalRecord){
        medicalRecordFacade.create(medicalRecord);
    }
    @PutMapping("/{id}")
    public void updateMedicalRecord(@PathVariable UUID id,
                                    @RequestBody MedicalRecordDTO medicalRecord){
        if(id != null) medicalRecordFacade.update(medicalRecord, id);
    }
    @DeleteMapping("/{id}")
    public void deleteMedicalRecord(@PathVariable UUID id){
        medicalRecordFacade.delete(id);
    }
}
