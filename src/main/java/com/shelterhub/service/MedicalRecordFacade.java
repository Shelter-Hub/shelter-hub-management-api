package com.shelterhub.service;

import com.shelterhub.database.MedicalRecordsRepository;
import com.shelterhub.domain.MedicalRecord;
import com.shelterhub.dto.MedicalRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MedicalRecordFacade {
    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    public MedicalRecordDTO create (MedicalRecordDTO medicalRecordDTO){
        MedicalRecord medicalRecord = new MedicalRecord();
        medicalRecord.setAnimal_id(medicalRecordDTO.getAnimal_id());
        medicalRecordsRepository.save(medicalRecord);
        medicalRecordDTO.setId(medicalRecord.getId());
        return medicalRecordDTO;
    }

    public MedicalRecordDTO update (MedicalRecordDTO medicalRecordDTO, UUID medicalRecordId){
        MedicalRecord medicalRecordInDb = medicalRecordsRepository.getReferenceById(medicalRecordId);
        medicalRecordInDb.setAnimal_id(medicalRecordDTO.getAnimal_id());
        return medicalRecordDTO;
    }

    public List<MedicalRecordDTO> getAllMedicalRecords () {
        return medicalRecordsRepository
                .findAll()
                .stream()
                .map(this::converter).collect(Collectors.toList());
    }

    public Optional<MedicalRecordDTO> getMedicalRecordById(UUID medicalRecordId) {
        return medicalRecordsRepository
                .findById(medicalRecordId)
                .map(this::converter);
    }

    public String delete (UUID medicalRecordId) {
        Optional<MedicalRecord> medicalRecordInDb = medicalRecordsRepository.findById(medicalRecordId);
        if (medicalRecordInDb.isEmpty()) {
            return "Animal not found";
        } else {
            medicalRecordsRepository.deleteById(medicalRecordId);
            return "Animal " + medicalRecordId + "was deleted successfully";
        }
    }

    private MedicalRecordDTO converter (MedicalRecord medicalRecord) {
        MedicalRecordDTO result = new MedicalRecordDTO();
        result.setId(medicalRecord.getId());
        result.setAnimal_id(medicalRecord.getAnimal_id());
        return result;
    }

}
