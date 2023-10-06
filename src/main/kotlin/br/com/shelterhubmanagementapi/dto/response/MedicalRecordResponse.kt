package br.com.shelterhubmanagementapi.dto.response

import java.util.UUID

data class MedicalRecordResponse(
    val id: UUID? = UUID.randomUUID(),
    val animalId: UUID,
)
