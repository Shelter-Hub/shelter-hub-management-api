package com.archersland.shelterhub.controller;

import com.archersland.shelterhub.database.MedicalRecordsRepository;
import com.archersland.shelterhub.domain.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/medical-record")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordsRepository.findAll();
    }
    @GetMapping("{id}")
    public Optional<MedicalRecord> getMedicalRecordsById(@PathVariable UUID id) {
        return medicalRecordsRepository.findById(id);
    }
    @PostMapping
    public void createMedicalRecord(@RequestBody MedicalRecord medicalRecord){
        medicalRecordsRepository.save(medicalRecord);
    }
    @PutMapping("{id}")
    public void updateMedicalRecord(@PathVariable UUID id,  @RequestBody MedicalRecord medicalRecord){
        if(id != null) medicalRecordsRepository.save(medicalRecord);
    }
    @DeleteMapping("{id}")
    public void deleteMedicalRecord(@PathVariable UUID id){
        medicalRecordsRepository.deleteById(id);
    }
}
