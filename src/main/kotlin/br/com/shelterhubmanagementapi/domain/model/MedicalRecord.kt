package br.com.shelterhubmanagementapi.domain.model

import br.com.shelterhubmanagementapi.dto.response.MedicalRecordResponse
import java.util.UUID

data class MedicalRecord(
    val id: UUID = UUID.randomUUID(),
    val animalId: UUID,
)

fun MedicalRecord.toResponse() =
    MedicalRecordResponse(
        id = this.id,
        animalId = this.animalId,
    )
