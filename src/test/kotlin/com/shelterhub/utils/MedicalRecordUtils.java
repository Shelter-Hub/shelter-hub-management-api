package com.shelterhub.utils;

import br.com.shelterhubmanagementapi.domain.model.MedicalRecord;
import br.com.shelterhubmanagementapi.dto.request.MedicalRecordRequest;

import java.util.UUID;

public class MedicalRecordUtils {

    public static MedicalRecord buildMedicalRecord() {

        return MedicalRecord.builder()
                .animalId(UUID.randomUUID())
                .id(UUID.randomUUID())
                .build();
    }


    public static MedicalRecordRequest buildMedicalRecordRequest() {
        return MedicalRecordRequest.builder()
                .animalId(UUID.randomUUID())
                .id(UUID.randomUUID())
                .build();
    }
}
