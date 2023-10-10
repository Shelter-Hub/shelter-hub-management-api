package br.com.shelterhubmanagementapi.domain.model

import br.com.shelterhubmanagementapi.domain.enums.AnimalType
import br.com.shelterhubmanagementapi.domain.enums.Gender
import br.com.shelterhubmanagementapi.domain.enums.Size
import br.com.shelterhubmanagementapi.dto.response.AnimalResponse
import kotlinx.datetime.LocalDate
import java.util.UUID

data class Animal(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val identification: String,
    val behavior: String,
    val breed: String,
    val history: String,
    val estimatedAge: LocalDate? = null,
    val medicalRecordId: UUID,
    val animalType: AnimalType,
    val gender: Gender,
    val size: Size,
)

fun Animal.toResponse(): AnimalResponse =
    AnimalResponse(
        id = this.id,
        name = this.name,
        identification = this.identification,
        behavior = this.behavior,
        breed = this.breed,
        history = this.history,
        estimatedAge = this.estimatedAge.toString(),
        medicalRecordId = this.medicalRecordId,
        animalType = this.animalType.toString(),
        gender = this.gender,
        size = this.size,
    )
