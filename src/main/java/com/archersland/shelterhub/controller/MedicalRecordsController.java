package com.archersland.shelterhub.controller;

import com.archersland.shelterhub.database.MedicalRecordsRepository;
import com.archersland.shelterhub.domain.Animal;
import com.archersland.shelterhub.domain.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medical-record")
public class MedicalRecordsController {

    private final MedicalRecordsRepository medicalRecordsRepository;

    @Autowired
   public MedicalRecordsController(MedicalRecordsRepository medicalRecordsRepository) {
        this.medicalRecordsRepository = medicalRecordsRepository;
    }

    @GetMapping
    public List<MedicalRecord> getAllMedicalRecords(@RequestBody MedicalRecord medicalRecords) {
        return medicalRecordsRepository.findAll();
    }
    @PostMapping
    public void createMedicalRecord(MedicalRecord medicalRecord){
        medicalRecordsRepository.save(medicalRecord);
    }

    @PutMapping
    public void updateMedicalRecord(MedicalRecord medicalRecord){
        if(medicalRecord.getId() != null) medicalRecordsRepository.save(medicalRecord);
    }

    @DeleteMapping
    public void deleteMedicalRecord(MedicalRecord medicalRecord){
        medicalRecordsRepository.delete(medicalRecord);
    }


//    @PostMapping("/medical-records")
//    public MedicalRecords createMedicalRecord(@RequestBody MedicalRecords medicalRecord) {
//
//    }
//
//    @GetMapping("medical-records/{id}")
//    public MedicalRecords getMedicalRecordsById(@PathVariable UUID id) {
//
//    }
//
//    @PutMapping("/medical-records/{id}")
//    public MedicalRecords updateMedicalRecords(@PathVariable UUID id, @RequestBody MedicalRecords medicalRecord) {
//
//    }
//
//    @DeleteMapping("/medical-records/{id}")
//    public void deleteMedicalRecord(@PathVariable UUID id) {
//
//    }

}
