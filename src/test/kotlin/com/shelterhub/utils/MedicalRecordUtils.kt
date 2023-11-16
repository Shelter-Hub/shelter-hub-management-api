package com.shelterhub.utils

import br.com.shelterhubmanagementapi.domain.model.MedicalRecord
import br.com.shelterhubmanagementapi.dto.request.MedicalRecordRequest
import br.com.shelterhubmanagementapi.dto.response.MedicalRecordResponse
import java.util.UUID

object MedicalRecordUtils {
    fun buildMedicalRecord() = MedicalRecord(
            animalId = UUID.randomUUID(),
            id = UUID.randomUUID()
        )

    fun buildMedicalRecordResponse() = MedicalRecordResponse(
        id = UUID.randomUUID(),
        animalId = UUID.randomUUID()
    )

    fun buildMedicalRecordRequest() = MedicalRecordRequest(
        animalId = UUID.randomUUID(),
        id = UUID.randomUUID()
    )
}
