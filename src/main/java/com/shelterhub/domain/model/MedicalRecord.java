package com.shelterhub.domain.model;

import com.shelterhub.dto.response.MedicalRecordResponse;
import com.shelterhub.exception.NotUpdatableException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Entity
@Data
@Table(name = "medical_record")
@Builder
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID animalId;

    public MedicalRecordResponse toResponse() {
        return MedicalRecordResponse.builder()
                .id(this.id)
                .animalId(this.animalId)
                .build();
    }

    public MedicalRecord copy(UUID uuid) {
        if (StringUtils.isBlank(animalId.toString()))
            throw new NotUpdatableException("The animalId to be updated is invalid.");

        return MedicalRecord.builder()
                .id(uuid)
                .animalId(this.animalId)
                .build();
    }
}
