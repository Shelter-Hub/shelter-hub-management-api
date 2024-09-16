package br.com.shelterhubmanagementapi.dto.request

import br.com.shelterhubmanagementapi.domain.model.MedicalRecord
import java.util.UUID

data class MedicalRecordRequest(
    val id: UUID = UUID.randomUUID(),
    val animalId: UUID
)

fun MedicalRecordRequest.toMedicalRecord() =
    MedicalRecord(
        id = this.id,
        animalId = this.animalId
    )
