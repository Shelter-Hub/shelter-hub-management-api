package com.archersland.shelterhub.database;

import com.archersland.shelterhub.domain.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicalRecordsRepository extends JpaRepository<MedicalRecord, UUID> {
}
