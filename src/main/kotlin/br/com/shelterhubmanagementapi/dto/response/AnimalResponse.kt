package br.com.shelterhubmanagementapi.dto.response

import br.com.shelterhubmanagementapi.domain.enums.Gender
import br.com.shelterhubmanagementapi.domain.enums.Size
import java.util.UUID

data class AnimalResponse(
    val id: String,
    val name: String,
    val identification: String,
    val behavior: String,
    val breed: String,
    val history: String,
    val estimatedAge: String? = null,
    val medicalRecordId: UUID,
    val animalType: String,
    val gender: Gender,
    val size: Size,
)
