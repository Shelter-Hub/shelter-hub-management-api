package br.com.shelterhubmanagementapi.dto.response

import br.com.shelterhubmanagementapi.domain.enums.Gender
import br.com.shelterhubmanagementapi.domain.enums.Size
import kotlinx.datetime.LocalDate
import java.util.UUID

data class AnimalResponse(
    val id: UUID,
    val name: String,
    val identification: String,
    val behavior: String,
    val breed: String,
    val history: String,
    val estimatedAge: LocalDate? = null,
    val medicalRecordId: UUID,
    val animalType: String,
    val gender: Gender,
    val size: Size,
)
