package com.shelterhub.database;

import com.shelterhub.domain.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MedicalRecordsRepository extends JpaRepository<MedicalRecord, UUID> {
}
