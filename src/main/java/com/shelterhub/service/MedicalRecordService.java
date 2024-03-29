package com.shelterhub.service;

import com.shelterhub.database.MedicalRecordsRepository;
import com.shelterhub.domain.model.MedicalRecord;
import com.shelterhub.dto.request.MedicalRecordRequest;
import com.shelterhub.dto.response.MedicalRecordResponse;
import com.shelterhub.exception.PersistenceFailedException;
import com.shelterhub.exception.UnknownErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicalRecordService {
    @Autowired
    private MedicalRecordsRepository medicalRecordsRepository;

    private static final Logger log = LoggerFactory.getLogger(MedicalRecordService.class);

    public MedicalRecordResponse create(MedicalRecordRequest medicalRecordRequest) {
        try {
            MedicalRecord medicalRecord = medicalRecordsRepository.save(medicalRecordRequest.toMedicalRecord());

            log.makeLoggingEventBuilder(Level.INFO)
                    .setMessage("Medical record was saved with success.")
                    .addKeyValue("medicalRecordId", medicalRecord.getId().toString())
                    .log();

            return medicalRecord.toResponse();
        } catch (DataAccessException ex) {
            throw new PersistenceFailedException(ex.getLocalizedMessage());
        } catch (Exception ex) {
            throw new UnknownErrorException(ex.getLocalizedMessage());
        }
    }

    public MedicalRecordResponse update(MedicalRecordRequest medicalRecordRequest, UUID uuid) {
        try {
            MedicalRecord medicalRecord = medicalRecordRequest.toMedicalRecord();

            var medicalRecordResult = medicalRecordsRepository.save(medicalRecord.copy(uuid));

            log.makeLoggingEventBuilder(Level.INFO)
                    .setMessage("Medical record was updated with success.")
                    .addKeyValue("medicalRecordId", medicalRecord.getId().toString())
                    .log();

            return medicalRecordResult.toResponse();

        } catch (DataAccessException ex) {
            throw new PersistenceFailedException(ex.getLocalizedMessage());
        } catch (Exception ex) {
            throw new UnknownErrorException(ex.getLocalizedMessage());
        }
    }

    public List<MedicalRecordResponse> getAll() {
        return medicalRecordsRepository
                .findAll()
                .stream()
                .map(MedicalRecord::toResponse)
                .toList();
    }

    public Optional<MedicalRecordResponse> getById(UUID medicalRecordId) {
        return medicalRecordsRepository
                .findById(medicalRecordId)
                .map(MedicalRecord::toResponse);
    }

    public Optional<MedicalRecord> deleteById(UUID id) {
        Optional<MedicalRecord> medicalRecordOptional = medicalRecordsRepository.findById(id);
        if (medicalRecordOptional.isEmpty()) {
            return Optional.empty();
        }

        medicalRecordsRepository.deleteById(id);

        log.makeLoggingEventBuilder(Level.INFO)
                .setMessage("Medical record was deleted with success.")
                .addKeyValue("medicalRecordId", id)
                .log();

        return medicalRecordOptional;
    }
}
