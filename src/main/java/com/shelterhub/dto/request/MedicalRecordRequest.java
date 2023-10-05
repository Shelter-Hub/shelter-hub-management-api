package com.shelterhub.dto.request;

import com.shelterhub.domain.model.MedicalRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordRequest {
    private UUID id;
    private UUID animalId;

    public MedicalRecord toMedicalRecord() {
        return MedicalRecord.builder()
                .id(getId())
                .animalId(getAnimalId())
                .build();
    }
}
